package ru.novotelnovad.ejb.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.novotelnovad.ejb.dao.interfaces.IDaoEJB;


/**
 * Реализация обобщеного абстрактного класса DAO 
 * Реализует обобщенный интерфейс IDaoEJB
 * @author Новотельнов А.Д.
 * @version 1.0
 * @param <E>
 */
public abstract class JPADaoEJB<E> implements IDaoEJB<E> {
	/** Экземпляр общенной сущности */
	protected Class<E> entityClass;
	
	@PersistenceContext
	protected EntityManager em;
	
	/**
	 * Конструктор по умолчанию 
	 * Получает тип обобщенного класса
	 */
	@SuppressWarnings("unchecked")
	public JPADaoEJB() {
		Class<?> cl = getClass();
		//Проверяем на возможность создания экземпляра
		 if (Object.class.getSimpleName().equals(cl.getSuperclass().getSimpleName())) {
			 throw new IllegalArgumentException(
					 "Default constructor can't create instance");
		 }

		 //
		 while
			 (!JPADaoEJB.class.getSimpleName().equals(cl.getSuperclass().getSimpleName())) {
			 if (cl.getGenericSuperclass() instanceof ParameterizedType) {
				 break;
			 }
			 cl = cl.getSuperclass();
		 }
		 if (cl.getGenericSuperclass() instanceof ParameterizedType) {
			 this.entityClass = (Class<E>) ((ParameterizedType) cl.getGenericSuperclass()).getActualTypeArguments()[0];	 
		 }
	}
	
	public void persist(E entity) {
		em.persist(entity);
	}
	
	/**
	 * Метод для удаления записи
	 * @param E
	 */
	public void remove(E entity) {
		entity = (E) em.merge(entity);
		em.remove(entity);
	}
	
	/**
	 * Метод осуществляет поиск сущности по ИД
	 * @param id ИД сущности
	 * @return Экземпляр сущности
	 */
	public E findById(int id) {
		return em.find(entityClass, id);
	}
}
