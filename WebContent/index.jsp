<%@ include file="header.jsp"%>

<section>
<h2>Réserver votre voyage</h2>

<form method="post" action="controller?action=seek" id="form_index">
   <p>
   		<%
   		String notice = (String)session.getAttribute("notice");
   		if(notice != null) {
   			out.println("<span id=\"notice\">" + session.getAttribute("notice") + "</span>" + "<br />");
   	   		session.setAttribute("notice", "");
   		}
   		%>
   
       <label for="depart">De :</label>
       <select name="depart" id="depart">
       <% 
       		ArrayList<String> a = (ArrayList<String>) application.getAttribute("airport_list");
       		for(int i = 0; i < a.size(); i++) {
       			if(i == 1)
       				out.println("<option value=\"" + a.get(i) + "\" selected>" + a.get(i) + "</option>");
       			else
       				out.println("<option value=\"" + a.get(i) + "\">" + a.get(i) + "</option>");
  			}
       %>
       </select>
       
       <br />
       <label for="arrival">À :</label>
       <select name="arrival" id="arrival">
       <% 
       		for(int i = 0; i < a.size(); i++) {
       			if(i == 0)
       				out.println("<option value=\"" + a.get(i) + "\" selected>" + a.get(i) + "</option>");
       			else
       				out.println("<option value=\"" + a.get(i) + "\">" + a.get(i) + "</option>");
       		}
       %>
       </select>
       
       <script>
			$(function(){
				$('#date_outbound').datepicker();
				$('#date_return').datepicker();
				});
		</script>
       
       <br />
       <label for="date_outbound">Date aller :</label>
       <input type="text" name="date_outbound" id="date_outbound" value="<% if(session.getAttribute("date_outbound") != null) out.println(session.getAttribute("date_outbound")); else out.println(application.getAttribute("date_outbound")); %>" required autofocus />
       <br />
       <label for="date_return">Date retour :</label>
       <input type="text" name="date_return" id="date_return" value="<% if(session.getAttribute("date_return") != null) out.println(session.getAttribute("date_return")); else out.println(application.getAttribute("date_return")); %>" required/>
       
       <br />
       <input type="submit" value="Envoyer" />
   </p>
</form>
</section>

<%@ include file="footer.html"%>