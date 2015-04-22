package ru.novotelnovad.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import ru.novotelnovad.classes.ConverterObjToStrJSON;
import ru.novotelnovad.ejb.dao.UserDaoEJB;
import ru.novotelnovad.ejb.local.UserEJBLocal;
import ru.novotelnovad.entity.jpa.User;

/**
 * @author Новотельнов А.Д.
 * @version 1.0
 * EJB Session Stateless
 * Класс реализующий логику работы с сущностью jpa / UserEJBDao.
 * Представляет методы для поиска / выборки / обновления / удаления - данных.
 */
@Stateless(name = "userEJB", mappedName = "ejb/userEJB")
public class UserEJB implements UserEJBLocal {

	@EJB(name = "userDaoEJB")
	private UserDaoEJB userDaoEJB;
	//@EJB(name = "taskEJB")
	//private TaskEJBLocal taskEJB;
	
	//Экземпляр класса - конвертер в JSON
	private ConverterObjToStrJSON<User> cvrt = new ConverterObjToStrJSON<User>();

	/**
	 * Получить(найти) или создать и получить пользователя по имени - имя пользователя уникальное!
	 * @param userName - искомое/создаваемое имя
	 * @return String - формата JSON
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
	 * Проверить на существование имени
	 * @param userName - искомое имя
	 * @return
	 */
	@Override
	public boolean checkExistName(String userName) {
		return checkExistAtName(userName);
	}
	
	/**
	 * Проверить на существование имени
	 * @param userName - искомое имя
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
	 * Т.К. поле имя в таблице Юзверов уникальное, нет смысла реализовать доп логику... и отслеживать его на уровне UI - js..
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
	 * Получить ID пользователя по имени - имя в сущносте/таблице уникальное
	 * @param name - искомое имя
	 * @return
	 */
	@Override
	public int getUserIdByName(String name) {
		return getIdUser(name);
	}

	/**
	 * Метод для поиска по ид
	 * @param id
	 * @return Объект типа USER
	 */
	@Override
	public User findById(int id) {
		return userDaoEJB.findById(id);
	}

	/**
	 * Получить всех пользователей по имени
	 * @param name - искомое имя
	 * @return List<User>
	 */
	@Override
	public List<User> getuser(String name) {
		return userDaoEJB.findByName(name);
	}
	
	
}
