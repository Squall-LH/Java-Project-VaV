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
			f.fill_basics();
		}
		catch (Exception e) {}
		
		AirportDAO aDAO = new AirportDAO();
		
		ArrayList<Airport> lA = new ArrayList<Airport>(aDAO.retrieveAll());
		
		ArrayList<String> lAS = new ArrayList<String>();

		for(Airport a : lA) {
			lAS.add(a.getName());
		}
		
		event.getServletContext().setAttribute("date1", "20/01/2001");
		event.getServletContext().setAttribute("date2", "20/01/2010");
		event.getServletContext().setAttribute("date_outbound", "25/12/2005");
		event.getServletContext().setAttribute("date_return", "05/01/2006");
		event.getServletContext().setAttribute("airport_list", lAS);
	}
}
