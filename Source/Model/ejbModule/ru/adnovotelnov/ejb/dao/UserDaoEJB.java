package ru.adnovotelnov.ejb.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

import com.google.gson.Gson;

import model_JPA.User;

/**
 * @author ����������� �.�.
 * @version 1.0
 * EJB Session Stateless
 * ����� ��������� ������ DAO ��� �������� user/jpa, ����������� �� �������������������� ������ JPADaoEJB
 * � ������� �� �������� model/jpa/User. ����� ��������� ����� ��� �������� User(jpa).
 * ������: ����� / ������� ������
 */
@Stateless(name = "userDaoEJB", mappedName = "ejb/userDaoEJB")
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserDaoEJB extends JPADaoEJB<User> {

	/**
	 * ������������ ������� ���� ������ �������������
	 * @return - List<Users>
	 */
	@Override
	public List<User> findAllEntities() {
		return em.createNamedQuery("User.findAll").getResultList();
	}

	/**
	 * ������������ ������� ���� ������������� �� ����
	 * @return - List<Users>
	 */
	public List<User> findByName(String custName) {
		return em.createNamedQuery("User.findByName").setParameter("custName", custName).getResultList(); 
	}
	
}