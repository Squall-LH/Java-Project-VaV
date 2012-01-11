<%@ include file="header.jsp"%>
<section>
<h2>Liste des réservations à venir et passées</h2>


<h3>Réservation passées</h3>
	
	   <ul>
       <% 
       		ArrayList<String> lRS_before = (ArrayList<String>) session.getAttribute("lRS_before");
       		for(String current : lRS_before) {
       			out.println("<li>" + current + "</li>");
       		}
       %>
       </ul>

<h3>Réservation à venir</h3>
<p>
	    <% 
		String notice = (String)session.getAttribute("notice");
   		if(notice != null) {
   			out.println("<span id=\"notice\">" + session.getAttribute("notice") + "</span>" + "<br />");
   	   		session.setAttribute("notice", "");
   		}
   		%>
</p>

<form method="post" action="controller?action=remove">
   
   		<ul>
       <% 
    		ArrayList<String> lRS_after_id = (ArrayList<String>) session.getAttribute("lRS_after_id");
       		ArrayList<String> lRS_after = (ArrayList<String>) session.getAttribute("lRS_after");
       		for(int i = 0; i < lRS_after_id.size(); i++) {
       			out.println("<li><input type=\"checkbox\" name=\"" + lRS_after_id.get(i) + "\" id=\"" + lRS_after_id.get(i) + "\" /> <label for=\"" + lRS_after_id.get(i) + "\">" + lRS_after.get(i) + "</label><br /></li>");
       		}
       %>
       </ul>
   <p>
       <br />
       <%
       if(lRS_after.size() > 0)
    	   out.println("<input type=\"submit\" value=\"Annuler la sélection\" />");
       %>
   </p>
</form>

</section>

<%@ include file="footer.html"%>