<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Create a gem</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/formGem.css"/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/menu.css"/>" />
	<script type="text/javascript" src="test.js"></script>
	<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
	<script type="text/javascript" src="createGemForm.js"></script>
</head>
<body>
	<table id="createGemForm">
	<tr>
		<td class="mainTable">
			<c:import url="menu.jsp" />
		</td>
		<td class="mainTable" >
			<div class="formGem">
				<c:import url="formulaire_gem.jsp" />
			</div>
		</td>
	</tr>
	</table>
</body>
</html>
