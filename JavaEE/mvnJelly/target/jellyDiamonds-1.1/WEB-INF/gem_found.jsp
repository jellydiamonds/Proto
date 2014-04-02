<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Find a gem</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/summaryGem.css"/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/menu.css"/>" />
</head>
<body>
	<table>
	<tr>
		<td class="mainContainer">
			<c:import url="menu.jsp" />
		</td>
		<td class="mainContainer">
			<c:import url="summary_gem.jsp" />
		</td>
	</tr>
	</table>
</body>
</html>