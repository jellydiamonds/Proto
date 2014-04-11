<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Gem ID</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/summaryGem.css"/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/menu.css"/>" />
	<link type="image/x-icon" rel="shortcut icon" href="inc/icon.png">
</head>
<body>
	<form name="jsonTestForm" action="/api/addGem/" method="post">
		<textarea rows="5" cols="30" name="jsonTextArea" ></textarea>
		<input type="submit" value="TRY REST WEB SERVICE!"/>	
	</form>
	
</body>
</html>