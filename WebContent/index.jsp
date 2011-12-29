<%@ include file="header.jsp"%>

<section>
<h2>Réserver votre voyage</h2>

<form method="post" action="reservation?action=reserve">
   <p>
       <label for="depart">De :</label>
       <input type="text" name="depart" id="depart" autofocus required />
       
       <br />
       <label for="arrival">À :</label>
       <input type="text" name="arrival" id="arrival" required />
       
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

<!--  
<jsp:useBean id="airport" class="com.VaV.model.Airport" scope="session"/>
<% //out.println(airport.getName()); %>
-->

<p>
<%  
/*
if(session.getAttribute("login") != null) out.println(session.getAttribute("login"));
if(session.getAttribute("pass") != null) out.println(session.getAttribute("pass"));
*/

if(user.getLevel() != com.VaV.model.User.VISITOR) {
	out.println(user.getFirst_name());
}
%>
</p>

<%@ include file="footer.html"%>