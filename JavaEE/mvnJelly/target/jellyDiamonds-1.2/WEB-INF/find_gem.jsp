<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Find a gem</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
	<link type="image/x-icon" rel="shortcut icon" href="inc/icon.png">
</head>
<body>
	<table>
	<tr>
		<td class="mainTable">
			<c:import url="menu.jsp" />
		</td>
		<td class="mainTable" width="350">
			<form method="post" action="<c:url value="/findGem"/>">
				<fieldset>
					<legend>Find a gem</legend>
					<label for="gemID">Gem #: <span class="required">*</span></label>
					<input type="text" id="gemID" name="gemID" value="" size="10" maxlength="10" />	
					<span class="error">${ requestScope.form.errors['gemID'] }</span> <br/>
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