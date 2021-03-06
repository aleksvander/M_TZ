package ru.novotelnovad.entity.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * Реализация сущности Task
 * @author Новотельнов А.Д.
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Task.findAll", query="SELECT t FROM Task t"),
	@NamedQuery(name="Task.findByName", query="SELECT t FROM Task t WHERE t.name = :custName")
})
public class Task implements Serializable {
	private static final long serialVersionUID = 8864201534819023201L;

	public Task(String name) { this (name, null); }
	
	public Task(String name, User user) { this (name, 0, user); }
	
	public Task(String name, Integer summTime, User user) { this (null, name, summTime, user); }
	
	public Task(Date lastTime, String name, Integer summTime, User user) {
		super();
		this.lastTime = lastTime;
		this.name = name;
		this.summTime = summTime;
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", lastTime=" + lastTime + ", name=" + name
				+ ", summTime=" + summTime + ", user=" + user + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_time")
	private Date lastTime;

	private String name;

	@Column(name="summ_time")
	private Integer summTime;

	//bi-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name="id_user", nullable = true)
	private User user;

	public Task() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSummTime() {
		return this.summTime;
	}

	public void setSummTime(Integer summTime) {
		this.summTime = summTime;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}