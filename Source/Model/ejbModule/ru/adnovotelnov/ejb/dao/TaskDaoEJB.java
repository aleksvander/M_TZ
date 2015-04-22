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
 * @author Новотельнов А.Д.
 * @version 1.0
 * EJB Session Stateless
 * Класс реализует шаблон DAO для сущности task/jpa, наследуется от параметризированного класса JPADaoEJB
 * в который мы передаем model/jpa/Task. Класс позволяет найти все сущности Task(jpa).
 * Методы: поиск / выборка данных
 */
@Stateless(name = "taskDaoEJB", mappedName = "ejb/taskDaoEJB")
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TaskDaoEJB extends JPADaoEJB<Task> {

	/**
	 * Осуществляет выборку всех данных задач
	 * @return - List<Task>
	 */
	@Override
	public List<Task> findAllEntities() {
		return em.createNamedQuery("Task.findAll").getResultList();
	}

	/**
	 * Осуществляет выборку всех данных пользователей по ид пользователя
	 * @param userId - ид пользователя
	 * @return - List<Task>
	 */
	public List<Task> findAllTaskByIdUser(Integer userId) {
		StringBuilder sb = new StringBuilder("SELECT t FROM Task t JOIN FETCH t.user u WHERE (t.user = " + userId + ")");
		return em.createQuery(sb.toString()).getResultList();
	}
	
	/**
	 * Осуществляет поиск задачи по имени связанных с ид конкретного пользователя
	 * @param custName - Имя задачи
	 * @param userId - ид пользователя
	 * @return - List<Task>
	 */
	public List<Task> findByNameAndUserId(String custName, int userId) {
		StringBuilder sb = new StringBuilder("SELECT t FROM Task t JOIN FETCH t.user u WHERE (t.name = '" + custName + "')");
		return em.createQuery(sb.toString()).getResultList();
	}

	/**
	 * Осуществляет поиск названия по имени
	 * @param custName - имя задачи
	 * @return - List<Task>
	 */
	public List<Task> findByName(String custName) {
		return em.createNamedQuery("Task.findByName").setParameter("custName", custName).getResultList();
	}
	
	/**
	 * Осуществляет поиск всех задач конкретного пользователя
	 * @param userId - ид пользователя
	 * @return - List<Task>
	 */
	public List<Task> findByAllTaskCurentUser(int userId) {
		StringBuilder sb = new StringBuilder("SELECT t FROM Task t JOIN FETCH t.user u WHERE t.user = " + userId);
		return em.createQuery(sb.toString()).getResultList();
	}
}
