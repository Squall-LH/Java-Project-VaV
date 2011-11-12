package com.VaV.model;
import javax.persistence.*;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	private String last_name;
	
	private String first_name;
	
	@Column(unique=true)
	private String login;
	
	private String pass;
	
	private int flights;

	public User() {

	}
}
