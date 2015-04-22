package ru.adnovotelnov.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;

import model_JPA.Task;
import model_JPA.User;
import ru.adnovotelnov.classes.ConverterObjToStrJSON;
import ru.adnovotelnov.classes.OtherCheckers;
import ru.adnovotelnov.ejb.dao.TaskDaoEJB;

/**
 * EJB Session Stateless
 * ����� ����������� ������ ������ � ��������� jpa / taskEJBDao.
 * ������������ ������ ��� ������ / ������� / ���������� / �������� - ������.
 * @author ����������� �.�.
 * @version 1.0
 */
@Stateless(mappedName = "taskEJB")
public class TaskEJB implements TaskEJBLocal {

	@EJB(name = "taskDaoEJB")
	private TaskDaoEJB taskDaoEJB;
	@EJB(name = "userEJB")
	private UserEJBLocal userEJB;
	
	//��������� ������ - ��������� � JSON
	private ConverterObjToStrJSON<Task> cvrt = new ConverterObjToStrJSON<Task>();
	private OtherCheckers oc;
	
	/**
	 * ����� ��������(������) / �������� � �������� ������
	 * @param userId - �� �������� ������������ "������"
	 * @param currentTask - �������� ������� ������
	 * @return string - ������� JSON
	 */
	@Override
	public String getOrCreateTask(Integer userId, String currentTask) {
		if (oc.checkNullable(currentTask) || oc.checkNullable(userId))
			return null;
		
		User user = null;
		Task task = null;
			try {
				user = userEJB.findById(userId);
				if (checkExistTask(currentTask)) {
					task = taskDaoEJB.findById(getIdTask(currentTask));//new Task(currentTask, user);
				} else {
					task = new Task(currentTask, user);
				}
				taskDaoEJB.persist(task);
			} catch (PersistenceException | NullPointerException e) {
				System.out.println("Exception : " + e);
				return null;
			}
		return cvrt.convertObjToStrTask(taskDaoEJB.findByNameAndUserId(currentTask, userId));
	}
	
	/**
	 * ����� �������� �� "�����" ���������� ������������� � ������� 
	 * �������� ��������� ������ � ������� "�������������" - � ��� ��� ��� �������
	 * @param userId - �� ������������
	 * @return
	 */
	private boolean checkAnyExistTaskForUser(Integer userId) {
		if (oc.checkNullable(userId)) {
			throw new NullPointerException();
		}
		if (taskDaoEJB.findAllTaskByIdUser(userId).size() != 0)
			return true;
		return false;
	}

	/**
	 * ����� �������� "��������������" ������� ������ � ������������
	 * @param userId - �� ������������
	 * @param currentTask - �������� ������
	 * @return ���������� ��������
	 */
	@Override
	public boolean checkTaskForUser(Integer userId, String currentTask) {
		if (oc.checkNullable(userId) || oc.checkNullable(currentTask))
			return false;
		
		List<Task> lt = taskDaoEJB.findByNameAndUserId(currentTask, userId);
		if (lt.size() != 0)
			if (lt.get(0).getUser().getId() == userId)
				return true;
		return false;
	}

	/**
	 * ����� �������� �� �������� � ������
	 * @param currentTask - �������� ������� ������
	 * @return ���������� ��������
	 */
	@Override
	public boolean checkExistTask(String currentTask) {
		if (taskDaoEJB.findByName(currentTask).size() != 0) 
			return true;
		else
			return false;
	}

	/**
	 * ����� ������������� ����� �������� ������������ � ������� �������
	 * @param userId - �� ������������
	 * @param taskid - �� ������
	 * @param timer - ����� �������������� � �������� * 60 * 24 ...
	 * @return ���������� ��������
	 */
	@Override
	public boolean setTaskTime(Integer userId, Integer taskId, Integer timer) {
		if (oc.checkNullable(taskId) || oc.checkNullable(timer))
			return false;
		
		//User user = userEJB.findById(userId);
		try {
			Task task = taskDaoEJB.findById(taskId);
			task.setSummTime(timer);
			taskDaoEJB.persist(task);
		} catch (PersistenceException e) {
			System.out.println("PersistenceException : " + e);
			return false;
		}
		return true;
	}
	
	/**
	 * ����� ���������� ����� �������� ������������
	 * @param userId - �� ������������
	 * @param currentTask - ��� ������
	 * @return - ����� �������������� � �������� * 60 * 24 ... 
	 */
	@Override
	public Integer getTimeForCurrentTaskAndHerUsers(Integer userId, String currentTask) {
		if (oc.checkNullable(currentTask))
			return 0;
		
		List<Task> tsk = taskDaoEJB.findByNameAndUserId(currentTask, userId);
		return tsk.get(0).getSummTime();
	}
	
	/**
	 * ����� ��� ������ �� ������������ �� �����
	 * �.�. ����� ������������� ����� ���� ������ !�����������!, �� ������ ��������� ����� ������...
	 * � ������ �� ������� JS �� ���������� ������, � ���������� ������ ���������� ��������
	 * @param userName
	 * @return
	 */
	private Integer getUserIdByCurrentTask(String userName) {
		return userEJB.getUserIdByName(userName);
	}

	/**
	 * ����� ���������� ��� ������ ������������
	 * @param userId - �� �������� ������������ "������"
	 * @return string - ������� JSON
	 */
	@Override
	public String getAllTask(Integer userId) {
		if (oc.checkNullable(userId))
			return null;
		return cvrt.convertObjToStrTask(taskDaoEJB.findByAllTaskCurentUser(userId));
	}
	
	/**
	 * ����� ��� �������� �� ������ �� �����
	 * @param name - ��� ������
	 * @return �� ������
	 */
	private int getIdTask(String name) {
		List<Task> lTask = taskDaoEJB.findByName(name);
		if (lTask.size() != 0) {
			return lTask.get(0).getId();
		} else {
			return 0;
		}
	}
}