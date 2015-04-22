package ru.adnovotelnov.ejb;

import java.util.ArrayList; 
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;

import model_JPA.Task;
import model_JPA.User;
import ru.adnovotelnov.classes.ConverterObjToStrJSON;
import ru.adnovotelnov.ejb.dao.UserDaoEJB;

/**
 * @author ����������� �.�.
 * @version 1.0
 * EJB Session Stateless
 * ����� ����������� ������ ������ � ��������� jpa / UserEJBDao.
 * ������������ ������ ��� ������ / ������� / ���������� / �������� - ������.
 */
@Stateless(name = "userEJB", mappedName = "ejb/userEJB")
public class UserEJB implements UserEJBLocal {

	@EJB(name = "userDaoEJB")
	private UserDaoEJB userDaoEJB;
	//@EJB(name = "taskEJB")
	//private TaskEJBLocal taskEJB;
	
	//��������� ������ - ��������� � JSON
	private ConverterObjToStrJSON<User> cvrt = new ConverterObjToStrJSON<User>();

	/**
	 * ��������(�����) ��� ������� � �������� ������������ �� ����� - ��� ������������ ����������!
	 * @param userName - �������/����������� ���
	 * @return String - ������� JSON
	 */
	@Override
	public String getOrCreateUser(String userName) {
		if (userName == null)
			return null;
		
		if (!checkExistAtName(userName)) {
			try {
				User user = new User(userName);
				userDaoEJB.persist(user);
			} catch (Exception e) {
				return null;
			}
		}
		return cvrt.convertObjToStrUser(userDaoEJB.findByName(userName));
	}
	
	/**
	 * ��������� �� ������������� �����
	 * @param userName - ������� ���
	 * @return
	 */
	@Override
	public boolean checkExistName(String userName) {
		return checkExistAtName(userName);
	}
	
	/**
	 * ��������� �� ������������� �����
	 * @param userName - ������� ���
	 * @return
	 */
	private boolean checkExistAtName(String userName) {
		if (userDaoEJB.findByName(userName).size() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * �.�. ���� ��� � ������� ������� ����������, ��� ������ ����������� ��� ������... � ����������� ��� �� ������ UI - js..
	 * @param name
	 * @return
	 */
	private int getIdUser(String name) {
		List<User> lUser = userDaoEJB.findByName(name);
		if (lUser.size() != 0) {
			return lUser.get(0).getId();
		} else {
			return 0;
		}
	}

	/**
	 * �������� ID ������������ �� ����� - ��� � ��������/������� ����������
	 * @param name - ������� ���
	 * @return
	 */
	@Override
	public int getUserIdByName(String name) {
		return getIdUser(name);
	}

	/**
	 * ����� ��� ������ �� ��
	 * @param id
	 * @return ������ ���� USER
	 */
	@Override
	public User findById(int id) {
		return userDaoEJB.findById(id);
	}

	/**
	 * �������� ���� ������������� �� �����
	 * @param name - ������� ���
	 * @return List<User>
	 */
	@Override
	public List<User> getuser(String name) {
		return userDaoEJB.findByName(name);
	}
	
	
}
