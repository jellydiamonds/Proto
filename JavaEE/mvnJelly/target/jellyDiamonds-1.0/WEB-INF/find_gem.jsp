<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Find a gem</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
</head>
<body>
	<table>
	<tr>
		<td class="mainContainer">
			<c:import url="/inc/menu.jsp" />
		</td>
		<td class="mainContainer" width="350">
			<form method="post" action="<c:url value="/findGem"/>">
				<fieldset>
					<legend>Find a gem</legend>
					<label for="gemId">Gem #: <span class="required">*</span></label>
					<input type="text" id="gemId" name="gemId" value="" size="10" maxlength="10" />	
					<span class="error">${ requestScope.form.errors['gemId'] }</span> <br/>
					<!-- <input type="submit" value="Find" />  -->
				</fieldset>
				<!-- <div class="buttons">
					
					<input type="reset" value="Reset" />
				</div> -->
				<p class="error">${ requestScope.form.errors['sql'] }</p>
				<p class="info">${ requestScope.form.result }</p>
			</form>
		</td>
	</tr>
	</table>
</body>
</html>