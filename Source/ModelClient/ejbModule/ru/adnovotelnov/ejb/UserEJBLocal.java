package ru.adnovotelnov.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model_JPA.User;

/**
 * ��������� ��������� ��� ������� � ejb ����������
 * � ����������� ��� ������� ����� RESTEasy
 * @author ����������� �.�.
 * @version 1.0
 */
@Local
@Path("users")
public interface UserEJBLocal {
	
	/**
	 * �������� ���� ������������� �� �����
	 * @param name - ������� ���
	 * @return List<User>
	 */
	public List<User> getuser(@PathParam("name") String name);
	
	/**
	 * ��������(�����) ��� ������� � �������� ������������ �� ����� - ��� ������������ ����������!
	 * @param userName - �������/����������� ���
	 * @return String - ������� JSON
	 */
	@GET
	@Path("getOrCreateUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrCreateUser(@CookieParam("username") @DefaultValue("") String userName);
	
	/**
	 * ��������� �� ������������� �����
	 * @param userName - ������� ���
	 * @return
	 */
	public boolean checkExistName(@CookieParam("username") @DefaultValue("") String userName);
	
	/**
	 * �������� ID ������������ �� ����� - ��� � ��������/������� ����������
	 * @param name - ������� ���
	 * @return
	 */
	public int getUserIdByName(String name);
	
	/**
	 * ����� ��� ������ �� ��
	 * @param id
	 * @return ������ ���� USER
	 */
	public User findById(@PathParam("id") int id);
}
