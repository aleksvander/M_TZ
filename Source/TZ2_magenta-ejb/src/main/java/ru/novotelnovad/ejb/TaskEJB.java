package ru.novotelnovad.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import ru.novotelnovad.classes.ConverterObjToStrJSON;
import ru.novotelnovad.classes.OtherCheckers;
import ru.novotelnovad.ejb.dao.TaskDaoEJB;

import ru.novotelnovad.ejb.local.TaskEJBLocal;
import ru.novotelnovad.ejb.local.UserEJBLocal;
import ru.novotelnovad.entity.jpa.Task;
import ru.novotelnovad.entity.jpa.User;

/**
 * EJB Session Stateless
 *  ласс реализующий логику работы с сущностью jpa / taskEJBDao.
 * ѕредставл¤ет методы дл¤ поиска / выборки / обновлени¤ / удалени¤ - данных.
 * @author Ќовотельнов ј.ƒ.
 * @version 1.0
 */
@Stateless(mappedName = "taskEJB")
public class TaskEJB implements TaskEJBLocal {

	@EJB(name = "taskDaoEJB")
	private TaskDaoEJB taskDaoEJB;
	@EJB(name = "userEJB")
	private UserEJBLocal userEJB;
	
	//Ёкземпл¤р класса -  онвертер в JSON
	private ConverterObjToStrJSON<Task> cvrt = new ConverterObjToStrJSON<Task>();
	private OtherCheckers oc;
	
	/**
	 * ћетод возврата(поиска) / создани¤ и возврата задачи
	 * @param userId - ид текущего пользовател¤ "сессии"
	 * @param currentTask - название текущей задачи
	 * @return string - формата JSON
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
			} catch (Exception e) {
				System.out.println("Exception : " + e);
				return null;
			}
		return cvrt.convertObjToStrTask(taskDaoEJB.findByNameAndUserId(currentTask, userId));
	}
	
	/**
	 * ћетод проверки на "любые" совпадени¤ пользователей в задачах 
	 * Ёксепшин геренирую только в методах "промежуточных" - в тех что без доступа
	 * @param userId - ид пользовател¤
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
	 * ћетод проверки "принадлежности" текущей задачи к пользователю
	 * @param userId - ид пользовател¤
	 * @param currentTask - название задачи
	 * @return логическое значение
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
	 * ћетод проверки на схожесть в именах
	 * @param currentTask - название текущей задачи
	 * @return логическое значение
	 */
	@Override
	public boolean checkExistTask(String currentTask) {
		if (taskDaoEJB.findByName(currentTask).size() != 0) 
			return true;
		else
			return false;
	}

	/**
	 * ћетод устанавливает врем¤ текущего пользовател¤ с текущей задачей
	 * @param userId - ид пользовател¤
	 * @param taskid - ид задачи
	 * @param timer - врем¤ целочискленное в секундах * 60 * 24 ...
	 * @return логическое значение
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
	 * ћетод возвращает врем¤ текущего пользовател¤
	 * @param userId - ид пользовател¤
	 * @param currentTask - им¤ задачи
	 * @return - врем¤ целочискленное в секундах * 60 * 24 ... 
	 */
	@Override
	public Integer getTimeForCurrentTaskAndHerUsers(Integer userId, String currentTask) {
		if (oc.checkNullable(currentTask))
			return 0;
		
		List<Task> tsk = taskDaoEJB.findByNameAndUserId(currentTask, userId);
		return tsk.get(0).getSummTime();
	}
	
	/**
	 * ћетод дл¤ поиска ид пользовател¤ по имени
	 * “. . »мена пользователей могут быть только !уникальными!, то вполне допустимо така¤ логика...
	 * ј значит на стороне JS не выкидывать данные, а возвращать только логическое значение
	 * @param userName
	 * @return
	 */
	private Integer getUserIdByCurrentTask(String userName) {
		return userEJB.getUserIdByName(userName);
	}

	/**
	 * ћетод возвращает все задачи пользовател¤
	 * @param userId - ид текущего пользовател¤ "сессии"
	 * @return string - формата JSON
	 */
	@Override
	public String getAllTask(Integer userId) {
		if (oc.checkNullable(userId))
			return null;
		return cvrt.convertObjToStrTask(taskDaoEJB.findByAllTaskCurentUser(userId));
	}
	
	/**
	 * ћетод дл¤ возврата ид задачи по имени
	 * @param name - им¤ задачи
	 * @return ид задачи
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
