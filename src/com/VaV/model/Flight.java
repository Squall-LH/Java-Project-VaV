package com.VaV.model;

import java.util.Calendar;
import javax.persistence.*;

@Entity
public class Flight implements java.io.Serializable {

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
	private Calendar date;

	public Flight() {

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

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}
