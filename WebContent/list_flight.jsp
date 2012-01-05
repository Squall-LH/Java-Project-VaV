<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<section>
<h2>Réserver votre voyage</h2>

<form method="post" action="controller?action=reserve">
   <p>
   		<% 
   		String notice = (String)session.getAttribute("notice");
   		if(notice != null) {
   			out.println("<span id=\"notice\">" + session.getAttribute("notice") + "</span>" + "<br />");
   	   		session.setAttribute("notice", "");
   		}
   		%>
   
       <label for="depart">Heure de départ :</label>
       <select name="depart" id="depart">
       <% 
       		ArrayList<Flight> lf_depart = (ArrayList<Flight>) session.getAttribute("lf_depart");
       		ArrayList<String> flight_depart = (ArrayList<String>) session.getAttribute("flight_depart");
       		for(int i = 0; i < lf_depart.size(); i++) {
       			out.println("<option value=\"" + lf_depart.get(i).getId() + "\">" + flight_depart.get(i) + "</option>");
       		}
       %>
       </select>
       
       <br />
       <label for="arrival">Heure d'arrivée :</label>
       <select name="arrival" id="arrival">
       <% 
       		ArrayList<Flight> lf_arrival = (ArrayList<Flight>) session.getAttribute("lf_arrival");
       		ArrayList<String> flight_arrival = (ArrayList<String>) session.getAttribute("flight_arrival");
       		for(int i = 0; i < lf_arrival.size(); i++) {
       			out.println("<option value=\"" + lf_arrival.get(i).getId() + "\">" + flight_arrival.get(i) + "</option>");
       		}
       %>
       </select>
 	
 	   <br />
 	   
       <%
       if(user.getLevel() == com.VaV.model.User.VISITOR) {
    	   out.println("Il est nécessaire de s'identifier avant de valider une réservation.");
       }
       else {
    	   out.println("Coût du billet :");
    	   Integer nb_reserve = (Integer)session.getAttribute("nb_reservation");
           if(nb_reserve != 0 && nb_reserve % 5 == 0) {
        	   out.println("Billet gratuit pour votre réservation nÂ°" + nb_reserve + " !");
           }
           else {
        	   out.println("20 €");
           }
           out.println("<input type=\"submit\" value=\"Réserver\" />");
       }	
       	%> 
   </p>
</form>
</section>

</section>

<%@ include file="footer.html"%>