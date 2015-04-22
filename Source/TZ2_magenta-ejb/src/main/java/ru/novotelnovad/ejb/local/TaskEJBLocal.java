package ru.novotelnovad.ejb.local;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Локальный интерфейс для доступа к ejb компоненту
 * С аннотациями для доступа через RESTEasy
 * @author Новотельнов А.Д.
 * @version 1.0
 */
@Local
@Path("tasks")
public interface TaskEJBLocal {
	
	/**
	 * Метод возврата(поиска) / создания и возврата задачи
	 * @param userId - ид текущего пользователя "сессии"
	 * @param currentTask - название текущей задачи
	 * @return string - формата JSON
	 */
	@GET
	@Path("getOrCreateTask")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrCreateTask(@CookieParam("userid") Integer userId, @CookieParam("currentTask") String currentTask);

	/**
	 * Метод возвращает все задачи пользователя
	 * @param userId - ид текущего пользователя "сессии"
	 * @return string - формата JSON
	 */
	@GET
	@Path("getAllTaskCurrentUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTask(@CookieParam("userid") Integer userId);
	
	/**
	 * Метод проверки "принадлежности" текущей задачи к пользователю
	 * @param userId - ид пользователя
	 * @param currentTask - название задачи
	 * @return логическое значение
	 */
	@GET
	@Path("checkTaskForUser")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkTaskForUser(@CookieParam("userid") Integer userId,
			@CookieParam("currentTask") String currentTask);

	/**
	 * Метод проверки на схожесть в именах
	 * @param currentTask - название текущей задачи
	 * @return логическое значение
	 */
	@GET
	@Path("checkExistTask")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkExistTask(@CookieParam("currentTask") String currentTask);
	
	/**
	 * Метод устанавливает время текущего пользователя с текущей задачей
	 * @param userId - ид пользователя
	 * @param taskid - ид задачи
	 * @param timer - время целочискленное в секундах * 60 * 24 ...
	 * @return логическое значение
	 */
	@GET
	@Path("setTaskTime/{currentTime}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean setTaskTime(@CookieParam("userid") Integer userId,
			@CookieParam("taskid") Integer taskid, @PathParam("currentTime") Integer timer);
	
	/**
	 * Метод возвращает время текущего пользователя
	 * @param userId - ид пользователя
	 * @param currentTask - имя задачи
	 * @return - время целочискленное в секундах * 60 * 24 ... 
	 */
	@GET
	@Path("getTimeTaskForUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Integer getTimeForCurrentTaskAndHerUsers(@CookieParam("userid") Integer userId,
			@CookieParam("currentTask") String currentTask);
}
