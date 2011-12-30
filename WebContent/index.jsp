<%@ include file="header.jsp"%>

<section>
<h2>Réserver votre voyage</h2>

<form method="post" action="user?action=reserve">
   <p>
       <label for="depart">De :</label>
       <select name="depart" id="depart">
       <% 
       		ArrayList<String> a = (ArrayList<String>) application.getAttribute("airport_list");
       		for(String s : a) {
       			out.println("<option value=\"" + s + "\">" + s + "</option>");
       		}
       %>
       </select>
       
       <br />
       <label for="arrival">À :</label>
       <select name="arrival" id="arrival">
       <% 
       		for(int i = 0; i < a.size(); i++) {
       			if(i != 1)
       				out.println("<option value=\"" + a.get(i) + "\">" + a.get(i) + "</option>");
       			else
       				out.println("<option value=\"" + a.get(i) + "\" selected>" + a.get(i) + "</option>");
       		}
       %>
       </select>
       
       <br />
       <label for="date_depart">Date aller :</label>
       <input type="text" name="date_depart" id="date_depart" required />
       
       <br />
       <label for="date_arrival">Date retour :</label>
       <input type="text" name="date_arrival" id="date_arrival" required/>
       
       <br />
       <input type="submit" value="Envoyer" />
   </p>
</form>
</section>

<%@ include file="footer.html"%>