package com.VaV.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Airport implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Column(unique = true)
	private String name;

	public Airport() {

	}
	
	public Airport(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
