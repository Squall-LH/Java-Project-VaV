package com.VaV.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String last_name;

	private String first_name;

	@Column(unique = true)
	private String login;

	private String pass;

	private int level;
	
	public final static int VISITOR = 0;
	public final static int USER = 1;
	public final static int ADMIN = 2;

	public User() {
		level = VISITOR;
	}
	
	public User(String last_name, String first_name, String login, String pass, int level) {
		this.last_name = last_name;
		this.first_name = first_name;
		this.login = login;
		this.pass = pass;
		this.level = level;
	}
	
	public User(User u) {
		this.last_name = u.last_name;
		this.first_name = u.first_name;
		this.login = u.login;
		this.pass = u.pass;
		this.level = u.level;
	}

	public void set(String last_name, String first_name, String login, String pass, int level) {
		this.last_name = last_name;
		this.first_name = first_name;
		this.login = login;
		this.pass = pass;
		this.level = level;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
