package ru.adnovotelnov.ejb.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import model_JPA.Task;
import model_JPA.User;

/**
 * @author ����������� �.�.
 * @version 1.0
 * EJB Session Stateless
 * ����� ��������� ������ DAO ��� �������� task/jpa, ����������� �� �������������������� ������ JPADaoEJB
 * � ������� �� �������� model/jpa/Task. ����� ��������� ����� ��� �������� Task(jpa).
 * ������: ����� / ������� ������
 */
@Stateless(name = "taskDaoEJB", mappedName = "ejb/taskDaoEJB")
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TaskDaoEJB extends JPADaoEJB<Task> {

	/**
	 * ������������ ������� ���� ������ �����
	 * @return - List<Task>
	 */
	@Override
	public List<Task> findAllEntities() {
		return em.createNamedQuery("Task.findAll").getResultList();
	}

	/**
	 * ������������ ������� ���� ������ ������������� �� �� ������������
	 * @param userId - �� ������������
	 * @return - List<Task>
	 */
	public List<Task> findAllTaskByIdUser(Integer userId) {
		StringBuilder sb = new StringBuilder("SELECT t FROM Task t JOIN FETCH t.user u WHERE (t.user = " + userId + ")");
		return em.createQuery(sb.toString()).getResultList();
	}
	
	/**
	 * ������������ ����� ������ �� ����� ��������� � �� ����������� ������������
	 * @param custName - ��� ������
	 * @param userId - �� ������������
	 * @return - List<Task>
	 */
	public List<Task> findByNameAndUserId(String custName, int userId) {
		StringBuilder sb = new StringBuilder("SELECT t FROM Task t JOIN FETCH t.user u WHERE (t.name = '" + custName + "')");
		return em.createQuery(sb.toString()).getResultList();
	}

	/**
	 * ������������ ����� �������� �� �����
	 * @param custName - ��� ������
	 * @return - List<Task>
	 */
	public List<Task> findByName(String custName) {
		return em.createNamedQuery("Task.findByName").setParameter("custName", custName).getResultList();
	}
	
	/**
	 * ������������ ����� ���� ����� ����������� ������������
	 * @param userId - �� ������������
	 * @return - List<Task>
	 */
	public List<Task> findByAllTaskCurentUser(int userId) {
		StringBuilder sb = new StringBuilder("SELECT t FROM Task t JOIN FETCH t.user u WHERE t.user = " + userId);
		return em.createQuery(sb.toString()).getResultList();
	}
}
