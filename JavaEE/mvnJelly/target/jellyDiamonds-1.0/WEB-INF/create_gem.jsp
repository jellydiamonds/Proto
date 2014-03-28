<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Create a gem</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
</head>
<body>
	<table id="createGemForm">
	<tr>
		<td class="mainContainer">
			<c:import url="/inc/menu.jsp" />
		</td>
		<td class="mainContainer" width="500" >
			<form method="post" action="<c:url value="/createGem"/>" accept-charset="UTF-8">
				<fieldset>
					<legend>Create a new gem</legend>
					<c:import url="/inc/formulaire_gem.jsp" />
				</fieldset>
				<div class="buttons">
					<input type="submit" value="Submit" />
					<input type="reset" value="Reset" />
				</div>
				<p class="info">${ requestScope.form.result }</p>
			</form>
		</td>
	</tr>
	</table>
</body>
</html>