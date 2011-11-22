<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.VaV.model.Airport"
    %>
<jsp:include page="header.html" />

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

<jsp:include page="footer.html" />
