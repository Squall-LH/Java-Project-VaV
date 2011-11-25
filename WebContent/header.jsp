<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Vol au Vent</title>
</head>
<body>
<header>
<h1>Vol au Vent</h1>
<nav>
<ul><li><a href="index.jsp">Accueil</a></li>
<% Integer user_level = new Integer((Integer)application.getAttribute("user_level"));
	
	if(user_level.compareTo(new Integer(0)) ==  0) {
		out.print("<li><a href=\"user.jsp?action=login\">S'identifier</a></li></ul>");
	}
	else {
		out.print("<li><a href=\"user.jsp?action=logout\">Se déconnecter</a></li></ul>");
	}
	if(user_level.compareTo(new Integer(0)) ==  2) {
		out.print("<li><a href=\"user.jsp?action=admin\">Administration</a></li></ul>");
	}
%>
</nav>
</header>