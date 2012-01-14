<%@ include file="header.jsp"%>

<section>
<h2>Inscription</h2>

<form method="post" action="controller?action=subscribe" id="form_index">
   <p>
   		<%
   		String notice = (String)session.getAttribute("notice");
   		if(notice != null) {
   			out.println("<span id=\"notice\">" + session.getAttribute("notice") + "</span>" + "<br />");
   	   		session.setAttribute("notice", "");
   		}
   		%>
   
       <label for="last_name">Votre nom :</label>
       <input type="text" name="last_name" id="last_name" required autofocus />
       <br />
       <label for="first_name">Votre prénom :</label>
       <input type="text" name="first_name" id="first_name" required />
       <br />
       <label for="login">Votre identifiant :</label>
       <input type="text" name="login" id="login" required />
       <br />
       <label for="pass">Votre mot de passe :</label>
       <input type="password" name="pass" id="pass" required />
       
       <br />
       <input type="submit" value="S'inscrire" />
   </p>
</form>
</section>

<%@ include file="footer.html"%>