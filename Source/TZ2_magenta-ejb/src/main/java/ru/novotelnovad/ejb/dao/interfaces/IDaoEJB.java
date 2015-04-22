package ru.novotelnovad.ejb.dao.interfaces;

import java.util.List;

/**
 * »нтерфейс - обновление/создание, удаление и поиск
 * @author Ќовотельнов ј.ƒ.
 * @version 1.0
 * @param <T>
 */
public interface IDaoEJB<T> {

	/**
	 * —оздание новой записи, либо обновление существующей
	 * @param entity Ёкземпл¤р сущности
	 */
	void persist(T entity);
	
	/**
	 * ”даление записи
	 * @param entity Ёкземпл¤р сущности
	 */
	void remove(T entity);
	
	/**
	 * ѕоиск всех записей
	 * @return —писок найденых сущностей
	 */
	List<T> findAllEntities();
}
