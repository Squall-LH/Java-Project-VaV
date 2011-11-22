package com.VaV.model;

import java.util.Calendar;

import javax.persistence.*;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@ManyToOne
	private Flight flight_outbound;

	@ManyToOne
	private Flight flight_return;

	@ManyToOne
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;

	public Reservation() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Flight getFlight_outbound() {
		return flight_outbound;
	}

	public void setFlight_outbound(Flight flight_outbound) {
		this.flight_outbound = flight_outbound;
	}

	public Flight getFlight_return() {
		return flight_return;
	}

	public void setFlight_return(Flight flight_return) {
		this.flight_return = flight_return;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}
