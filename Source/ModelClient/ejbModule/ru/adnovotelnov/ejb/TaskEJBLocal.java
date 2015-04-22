package ru.adnovotelnov.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model_JPA.Task;

/**
 * ��������� ��������� ��� ������� � ejb ����������
 * � ����������� ��� ������� ����� RESTEasy
 * @author ����������� �.�.
 * @version 1.0
 */
@Local
@Path("tasks")
public interface TaskEJBLocal {
	
	/**
	 * ����� ��������(������) / �������� � �������� ������
	 * @param userId - �� �������� ������������ "������"
	 * @param currentTask - �������� ������� ������
	 * @return string - ������� JSON
	 */
	@GET
	@Path("getOrCreateTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrCreateTask(@CookieParam("userid") Integer userId, @CookieParam("currentTask") String currentTask);

	/**
	 * ����� ���������� ��� ������ ������������
	 * @param userId - �� �������� ������������ "������"
	 * @return string - ������� JSON
	 */
	@GET
	@Path("getAllTaskCurrentUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTask(@CookieParam("userid") Integer userId);
	
	/**
	 * ����� �������� "��������������" ������� ������ � ������������
	 * @param userId - �� ������������
	 * @param currentTask - �������� ������
	 * @return ���������� ��������
	 */
	@GET
	@Path("checkTaskForUser")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkTaskForUser(@CookieParam("userid") Integer userId,
			@CookieParam("currentTask") String currentTask);

	/**
	 * ����� �������� �� �������� � ������
	 * @param currentTask - �������� ������� ������
	 * @return ���������� ��������
	 */
	@GET
	@Path("checkExistTask")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkExistTask(@CookieParam("currentTask") String currentTask);
	
	/**
	 * ����� ������������� ����� �������� ������������ � ������� �������
	 * @param userId - �� ������������
	 * @param taskid - �� ������
	 * @param timer - ����� �������������� � �������� * 60 * 24 ...
	 * @return ���������� ��������
	 */
	@GET
	@Path("setTaskTime/{currentTime}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean setTaskTime(@CookieParam("userid") Integer userId,
			@CookieParam("taskid") Integer taskid, @PathParam("currentTime") Integer timer);
	
	/**
	 * ����� ���������� ����� �������� ������������
	 * @param userId - �� ������������
	 * @param currentTask - ��� ������
	 * @return - ����� �������������� � �������� * 60 * 24 ... 
	 */
	@GET
	@Path("getTimeTaskForUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Integer getTimeForCurrentTaskAndHerUsers(@CookieParam("userid") Integer userId,
			@CookieParam("currentTask") String currentTask);
}