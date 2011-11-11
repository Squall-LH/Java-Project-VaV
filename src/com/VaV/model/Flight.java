package com.VaV.model;

public class Flight {

	protected int id;
	protected int id_airport_depart;
	protected int id_airport_arrival;
	protected int id_plane;
	protected String date;

	public Flight() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_airport_depart() {
		return id_airport_depart;
	}

	public void setId_airport_depart(int id_airport_depart) {
		this.id_airport_depart = id_airport_depart;
	}

	public int getId_airport_arrival() {
		return id_airport_arrival;
	}

	public void setId_airport_arrival(int id_airport_arrival) {
		this.id_airport_arrival = id_airport_arrival;
	}

	public int getId_plane() {
		return id_plane;
	}

	public void setId_plane(int id_plane) {
		this.id_plane = id_plane;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
