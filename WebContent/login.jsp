<%@ include file="header.jsp"%>

<section>
<h2>Vous identifier</h2>

<form method="post" action="user?action=login">
   <p>       
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