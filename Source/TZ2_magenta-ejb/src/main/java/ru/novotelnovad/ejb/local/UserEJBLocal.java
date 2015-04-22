package ru.novotelnovad.ejb.local;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ru.novotelnovad.entity.jpa.User;


/**
 * Локальный интерфейс для доступа к ejb компоненту
 * С аннотациями для доступа через RESTEasy
 * @author Новотельнов А.Д.
 * @version 1.0
 */
@Local
@ApplicationPath("rest")
@Path("users")
public interface UserEJBLocal {
	
	/**
	 * Получить всех пользователей по имени
	 * @param name - искомое имя
	 * @return List<User>
	 */
	public List<User> getuser(@PathParam("name") String name);
	
	/**
	 * Получить(найти) или создать и получить пользователя по имени - имя пользователя уникальное!
	 * @param userName - искомое/создаваемое имя
	 * @return String - формата JSON
	 */
	@GET
	@Path("getOrCreateUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrCreateUser(@CookieParam("username") @DefaultValue("") String userName);
	
	/**
	 * Проверить на существование имени
	 * @param userName - искомое имя
	 * @return
	 */
	public boolean checkExistName(@CookieParam("username") @DefaultValue("") String userName);
	
	/**
	 * Получить ID пользователя по имени - имя в сущносте/таблице уникальное
	 * @param name - искомое имя
	 * @return
	 */
	public int getUserIdByName(String name);
	
	/**
	 * Метод для поиска по ид
	 * @param id
	 * @return Объект типа USER
	 */
	public User findById(@PathParam("id") int id);
}
