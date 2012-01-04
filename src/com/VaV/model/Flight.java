package com.VaV.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Flight implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@ManyToOne
	private Airport airport_depart;

	@ManyToOne
	private Airport airport_arrival;

	@ManyToOne
	private Plane plane;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Flight() {

	}
	
	public void set(Airport airport_depart, Airport airport_arrival, Plane plane, Date date) {
		this.airport_depart = airport_depart;
		this.airport_arrival = airport_arrival;
		this.plane = plane;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Airport getAirport_depart() {
		return airport_depart;
	}

	public void setAirport_depart(Airport airport_depart) {
		this.airport_depart = airport_depart;
	}

	public Airport getAirport_arrival() {
		return airport_arrival;
	}

	public void setAirport_arrival(Airport airport_arrival) {
		this.airport_arrival = airport_arrival;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
