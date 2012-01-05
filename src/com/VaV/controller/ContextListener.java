package com.VaV.controller;


import java.util.ArrayList;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.VaV.execute.Fill;
import com.VaV.model.*;
import com.VaV.persistence.*;

import javax.servlet.annotation.WebListener;
@WebListener
public class ContextListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent event) {}

	public void contextInitialized(ServletContextEvent event) {		
		try {
			Fill f = new Fill();
			f.fill_basic_user();
		}
		catch (Exception e) {}
		
		AirportDAO aDAO = new AirportDAO();
		
		ArrayList<Airport> lA = new ArrayList<Airport>(aDAO.retrieveAll());
		
		ArrayList<String> lAS = new ArrayList<String>();
		System.out.println("******************************" + lA.size());
		for(Airport a : lA) {
			lAS.add(a.getName());
			System.out.println("Airport : " + a.getName() );
		}
		
		event.getServletContext().setAttribute("airport_list", lAS);
	}
}
