<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.VaV.model.Airport"
    %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Vol au Vent</title>
</head>
<body>
<jsp:useBean id="user" class="com.VaV.model.User" scope="session"/>
<%@ page import="java.util.ArrayList" %>
<header>
<h1>Vol au Vent</h1>
<nav>
<ul><li><a href="index.jsp">Accueil</a></li>
<% 
	if(user.getLevel() != com.VaV.model.User.VISITOR) {
		out.print("<li><a href=\"user?action=logout\">Se déconnecter</a></li></ul>");
		
		if(user.getLevel() == com.VaV.model.User.ADMIN) {
			out.print("<li><a href=\"user?action=admin\">Administration</a></li></ul>");
		}
	}
	else {
		out.print("<li><a href=\"login.jsp\">S'identifier</a></li></ul>");
	}

	out.println("<p>");
	if(user.getLevel() != com.VaV.model.User.VISITOR) {
		out.println("Bienvenu " + user.getFirst_name() + user.getLast_name());
	}
%>
</p>
</nav>
</header>