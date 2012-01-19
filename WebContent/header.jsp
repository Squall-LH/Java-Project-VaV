<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.VaV.model.Airport"
    import="com.VaV.model.Flight"
    %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="general.css" />
<link type="text/css" href="js/css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
<script src="js/jquery-1.6.2.min.js"></script>
<script src="js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.ui.datepicker-fr.js"></script>
<title>Vol au Vent</title>
</head>
<body>
<div id="document">
<jsp:useBean id="user" class="com.VaV.model.User" scope="session"/>
<%@ page import="java.util.ArrayList" %>
<header>
<h1>Vol au Vent</h1>
<nav>
<ul><li><a href="index.jsp">Accueil</a></li>
<% 
	if(user.getLevel() != com.VaV.model.User.VISITOR) {
		out.print("<li><a href=\"controller?action=logout\">Se déconnecter</a></li>");
		out.print("<li><a href=\"controller?action=list_reservation\">Liste des réservations</a></li>");
		
		if(user.getLevel() == com.VaV.model.User.ADMIN) {
			out.print("<li><a href=\"controller?action=fill_database\">Remplir la BDD</a></li>");
			out.print("<li><a href=\"view_flight.jsp\">Voir la liste des vols</a></li>");
		}
	}
	else {
		out.print("<li><a href=\"login.jsp\">S'identifier</a></li>");
		out.print("<li><a href=\"subscribe.jsp\">S'inscrire</a></li>");
	}

	out.println("</ul><p>");
	if(user.getLevel() != com.VaV.model.User.VISITOR) {
		out.println("Bienvenue " + user.getFirst_name() + " " + user.getLast_name());
	}
%>
</p>
</nav>
</header>