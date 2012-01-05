<%@ include file="header.jsp"%>

<section>
<h2>Vous identifier</h2>

<form method="post" action="user?action=login" id="form_login">
   <p>  
   		<% 
   		String notice = (String)session.getAttribute("notice");
   		if(notice != null) {
   			out.println("<span id=\"notice\">" + session.getAttribute("notice") + "</span>" + "<br />");
   	   		session.setAttribute("notice", "");
   		}
   		%>
   	
 	   <label for="id">Votre identifiant :</label>
       <input type="text" name="id" id="id" autofocus required />
       
       <br />
       <label for="pass">Votre mot de passe :</label>
       <input type="password" name="pass" id="pass" required />
       
       <br />
       <input type="submit" value="Envoyer" />
   </p>
</form>
</section>

<%@ include file="footer.html"%>