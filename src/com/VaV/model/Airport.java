package com.VaV.model;

import javax.persistence.*;

@Entity
public class Airport implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Column(unique = true)
	private String name;

	public Airport() {

	}

	public void clone(Airport a) {
		this.name = a.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
