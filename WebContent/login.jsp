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
 	   <label for="id">Votre identifiant :</label>
       <input type="text" name="id" id="id" autofocus required />
       
       <br />
       <label for="pass">Votre mot de passe :</label>
       <input type="password" name="pass" id="pass" required />
   </p>
</form>
</section>

<!--  
<jsp:useBean id="airport" class="com.VaV.model.Airport" scope="session"/>
<% //out.println(airport.getName()); %>
-->

<jsp:include page="footer.html" />