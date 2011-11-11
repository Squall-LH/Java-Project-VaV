package com.VaV.model;

public class Reservation {

	protected int id;
	protected int id_flight_outbound;
	protected int id_flight_return;
	protected int id_user;
	protected String date;

	public Reservation() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_flight_outbound() {
		return id_flight_outbound;
	}

	public void setId_flight_outbound(int id_flight_outbound) {
		this.id_flight_outbound = id_flight_outbound;
	}

	public int getId_flight_return() {
		return id_flight_return;
	}

	public void setId_flight_return(int id_flight_return) {
		this.id_flight_return = id_flight_return;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
