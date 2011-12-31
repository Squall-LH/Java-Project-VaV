package com.VaV.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.VaV.model.Flight;
import com.VaV.model.Reservation;
import com.VaV.model.User;

public class ReservationDAO extends DAO<Reservation> {

	public ReservationDAO() {
		
	}
	
	public ArrayList<Reservation> retriveInsider(Date d1, Date d2, User u) {
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		String query = new String("select r.id from RESERVATION r join FLIGHT f1 on r.FLIGHT_OUTBOUND_ID=f1.id join FLIGHT f2 on r.FLIGHT_RETURN_ID = f2.id where '"
				+ format.format(d1) + "' between f1.date and f2.date AND '" + format.format(d2) + "' between f1.date and f2.date AND r.user_id = " + u.getId());
		
		Connection cnx=null;
	    Reservation r = new Reservation();
	    
		try {
			String url="jdbc:mysql://localhost:3306/vav";
			cnx = (Connection) DriverManager.getConnection(url, "vol", "vent");
			
			System.out.println(query);
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				String id = new String(rs.getString("id"));
				System.out.println("******************************************************** Reservation chevauchement  " + id);
			
				int id_int = Integer.parseInt(id);
				r.setId(id_int);
				r = find(r);
				results.add(r);
			}
			
			rs.close();
			cnx.close();
				
		} catch(Exception  e) {
			e.printStackTrace(System.out);
		}
		
		return results;
	}
}
