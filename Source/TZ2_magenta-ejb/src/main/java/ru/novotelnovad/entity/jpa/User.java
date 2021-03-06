package ru.novotelnovad.entity.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * Реализация сущности User
 * @author Новотельнов А.Д.
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.findByName", query="SELECT u FROM User u WHERE u.name = :custName")
	//@NamedQuery(name="User.findByName", query="SELECT u FROM User as u join fetch u.tasks WHERE u.name = :custName")
})
public class User implements Serializable {
	private static final long serialVersionUID = 5033132271392778305L;

	public User(String name) { this(name, null); }
	
	public User(String name, List<Task> tasks) {
		super();
		this.name = name;
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL})
	private List<Task> tasks;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setUser(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setUser(null);

		return task;
	}

}