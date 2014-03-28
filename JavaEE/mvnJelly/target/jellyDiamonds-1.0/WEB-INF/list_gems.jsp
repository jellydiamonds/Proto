<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Gems List</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css"/>" />
	<link type="icon" rel="image/png" href="/inc/icon.png" />
</head>
<body>
	<table>
	<tr>
		<td class="mainContainer">
			<c:import url="/inc/menu.jsp" />
		</td>
		
		<td class="mainContainer">
			<div id="list">
			<c:choose>
				<%-- Si aucune gemme n'existe en session, affichage d'un message par défaut --%>
				<c:when test="${ empty sessionScope.sessionGemsList }">	
					<p class="error">No gems in the list.</p>
				</c:when>
				<%-- Sinon, affichage du tableau --%>
				<c:otherwise>
					<h1>.:GEMS:.</h1>
					Total: 
					
					<table>
						<tr>
							<th>#</th>
							<th>Type</th>
							<th>Color</th>
							<th class="delete">Delete</th>
						</tr>
						<c:forEach var="gem" items="${ sessionScope.sessionGemsList }" varStatus="loop">
						<tr class=${ loop.index % 2 == 0 ? 'whiteLine' : 'grayLine' }>
							<td class="id">${ gem.id }</td>
							<td class="gemType">${ gem.type }</td>
							<td class="gemColor">${ gem.color }</td>
							<td>
								<a href="<c:url value="/deleteGem"><c:param name="idGem" 
								value="${ gem.id }"/></c:url>">
									<img src="<c:url value="/inc/cross.png"/>" alt="delete gem" width="15" height="15"/>
								</a>
							</td>
						</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
			</div>	
		</td>
	</tr>
	</table>
	
</body>
</html>