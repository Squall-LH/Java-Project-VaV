package com.VaV.model;

import javax.persistence.*;

@Entity
public class Airport {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(unique=true)
	private String name;

	public Airport() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
