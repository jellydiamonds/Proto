<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Gems List</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/listGems.css"/>">
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/menu.css"/>">
	<link type="image/x-icon" rel="shortcut icon" href="inc/icon.png">
</head>
<body>
	<table>
	<tr>
		<td class="mainTable">
			<c:import url="menu.jsp" />
		</td>
		<td class="mainTable">
			<div id="gemsList">
			<c:choose>
				<%-- If the MAP of gems is empty, display a default message. --%>
				<c:when test="${ empty sessionScope.sessionGemsMap }">	
					<p class="error">No gems in the list.</p>
				</c:when>
				<%-- Otherwise, display a table. --%>
				<c:otherwise>
					<h1>.:GEMS:.</h1>
					<table id="gemsListTable">
						<tr>
							<th>Photo</th>
							<!-- <th>Light</th>
							<th>Gem-ID #</th>-->
							<!-- <th>Creation date</th> -->
							<th>Gem reference</th>
							<th>Species</th>
							<!-- <th>Color</th>
							<th>Shape</th>
							<th>Cut</th> -->
							<th>Mass (cts)</th>
							<th>Dimensions (mm)</th>
							<!-- <th>Clarity</th>
							<th>Enhancement</th>
							<th>Origin</th>
							<th>Certificate</th>
							<th>Comments</th> -->
							<th>Price (per 1 cts)</th>
							<th>Supplier ID</th>
							<th class="delete">Delete</th>
						</tr>
						<c:forEach var="gemEntry" items="${ sessionScope.sessionGemsMap }" varStatus="loop">
						<c:set var="gem" scope="request" value="${ gemEntry.value }" />
						<tr class=${ loop.index % 2 == 0 ? 'blackLine' : 'darkGrayLine' }
							onclick="document.location = '<c:url value="/gems"><c:param name="gemID" value="${ gem.id }"/></c:url>';">
							
							<td class="gemPhoto">
				<%-- If gem's photo exists, we display a thumbnail of a photo which is a link to original image.--%>
								<c:if test="${ !empty gem.photoLink }">
									<img src="<c:url value="/images/${ gem.photoLink }"/>" alt="Gem # ${ gem.id }" width="100"/>
								</c:if>
							</td>
							<!-- <td class="gemLight">
								<c:out value="${ applicationScope.arrayLight[gem.light] }"/>
							</td>
							<td class="gemID">
								<c:out value="${ gem.id }"/>
							</td>-->
							<!-- <td class="gemDate">
								<c:out value="${ gem.creationDate }"/>
							</td> -->
							<td class="gemReference">
								<c:out value="${ gem.reference }"/>
							</td>
							<td class="gemSpecies">
								<c:out value="${ applicationScope.arraySpecies[gem.species] }"/>
							</td>
							<!-- <td class="gemColor">
								<c:out value="${ gem.color }"/>
							</td>
							<td class="gemShape">
								<c:out value="${ applicationScope.arrayShape[gem.shape] }"/>
							</td>
							<td class="gemCut">
								<c:out value="${ applicationScope.arrayCut[gem.cut] }"/>
							</td>-->
							<td class="gemMass">
								<c:out value="${ gem.mass }"/>
							</td>
							<td class="gemSize">
								<c:out value="${ gem.sizeX } x ${ gem.sizeY }"/>
								<c:if test="${ !empty gem.sizeZ }">
									<c:out value=" x ${ gem.sizeZ }"/>
								</c:if>
							</td>
							<!-- <td class="gemClarity">
								<c:out value="${ applicationScope.arrayClarity[gem.clarity] }"/>
							</td>
							<td class="gemEnhancement">
								<c:out value="${ applicationScope.arrayEnhancement[gem.enhancement] }"/>
							</td>
							<td class="gemOrigin">
								<c:out value="${ applicationScope.arrayOrigin[gem.origin] }"/>
							</td>
							<td class="gemCertificate">
								<c:out value="${ applicationScope.arrayCertificate[gem.certificate] }"/>
							</td>
							<td class="gemComments">
								<c:out value="${ gem.comments }"/>
							</td> -->
							<td class="gemPrice">
								<c:out value="${ gem.priceValue } ${ applicationScope.arrayCurrency[gem.priceCurrency] }"/>
							</td>
							<td class="gemSupplierID">
								<c:out value="${ gem.supplierID }"/>
							</td>
							<td>
								<a href="<c:url value="/deleteGem"><c:param name="gemID" 
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