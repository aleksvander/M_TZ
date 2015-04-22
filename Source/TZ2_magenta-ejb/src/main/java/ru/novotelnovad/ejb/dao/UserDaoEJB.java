package ru.novotelnovad.ejb.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

import com.google.gson.Gson;
import ru.novotelnovad.entity.jpa.User;

/**
 * @author Новотельнов А.Д.
 * @version 1.0
 * EJB Session Stateless
 * Класс реализует шаблон DAO для сущности user/jpa, наследуется от параметризированного класса JPADaoEJB
 * в который мы передаем model/jpa/User. Класс позволяет найти все сущности User(jpa).
 * Методы: поиск / выборка данных
 */
@Stateless(name = "userDaoEJB", mappedName = "ejb/userDaoEJB")
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserDaoEJB extends JPADaoEJB<User> {

	/**
	 * Осуществляет выборку всех данных пользователей
	 * @return - List<Users>
	 */
	@Override
	public List<User> findAllEntities() {
		return em.createNamedQuery("User.findAll").getResultList();
	}

	/**
	 * Осуществляет выборку всех пользователей по мени
	 * @return - List<Users>
	 */
	public List<User> findByName(String custName) {
		return em.createNamedQuery("User.findByName").setParameter("custName", custName).getResultList(); 
	}
	
}
