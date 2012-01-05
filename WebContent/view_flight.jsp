<%@ include file="header.jsp"%>

<section>
<h2>Réserver votre voyage</h2>

<h3>Période</h3>
<form method="post" action="user?action=view_flight">
   <p>
   		<% 
   		String notice = (String)session.getAttribute("notice");
   		if(notice != null) {
   			out.println("<span id=\"notice\">" + session.getAttribute("notice") + "</span>" + "<br />");
   	   		session.setAttribute("notice", "");
   		}
   		%>
     
       <br />
       <label for="date1">Date 1</label>
       <input type="text" name="date1" id="date1" required />
       
       <br />
       <label for="date2">Date 2</label>
       <input type="text" name="date2" id="date2" required/>
       
       <br />
       <input type="submit" value="Envoyer" />
   </p>
</form>

	<%
	
	if(session.getAttribute("lFlightS") != null) {
		
		ArrayList<String> lFlightS = (ArrayList<String>)session.getAttribute("lFlightS");
		
		out.println("<p>");
		
		out.println("<ul>");
		
		for(String current : lFlightS) {
			out.println("<li>" + current + "</li>");	
		}
		
		out.println("</ul>");
		
		out.println("</p>");
		
	}
	
	%>

</section>

<%@ include file="footer.html"%>