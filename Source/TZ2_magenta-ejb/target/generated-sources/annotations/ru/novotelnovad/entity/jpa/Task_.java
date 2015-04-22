package ru.novotelnovad.entity.jpa;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ {

	public static volatile SingularAttribute<Task, Date> lastTime;
	public static volatile SingularAttribute<Task, Integer> summTime;
	public static volatile SingularAttribute<Task, String> name;
	public static volatile SingularAttribute<Task, Integer> id;
	public static volatile SingularAttribute<Task, User> user;

}

