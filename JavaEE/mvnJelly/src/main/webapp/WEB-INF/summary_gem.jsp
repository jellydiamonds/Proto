<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="gem" scope="request" value="${ requestScope.currentGem }"/>

<p class="info">${ requestScope.form.result }</p>

<div id="gemIDcontainer">
<table id="recapTable">
	<tr>
		<td colspan="2">
			<div class="buttonsContainer">
				<div class="button" tabindex="1"/>
					<a href="<c:url value="/editGem?gemID=${ gem.id }"/>">
						Edit
					</a>
				</div>
				<div class="button" tabindex="2"/>
					<a href="<c:url value="/deleteGem?gemID=${ gem.id }"/>">
						Delete
					</a>
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td class="gemPhoto" colspan="2">
			<%-- If gem's photo exists, we display a thumbnail of a photo which is a link to original image.--%>
			<c:if test="${ !empty gem.photoLink }">
				<a href="<c:url value="/images/${ gem.photoLink }"/>">
					<img src="<c:url value="/images/${ gem.photoLink }"/>" alt="Gem # ${ gem.id }" width="400"/>
				</a>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="field_title">Photo taken under:</td>
		<td class="field_value"><c:out value="${ applicationScope.arrayLight[gem.light] }"/></td>
	<tr>
		<td class="field_title">GemID #</td>
		<td class="field_value"><c:out value="${ gem.id }" /></td>
	</tr>
	<tr>
		<td class="field_title">GemID creation date</td>
		<td class="field_value"><c:out value="${ gem.creationDate }" /></td>
	</tr>
	<tr>
		<td class="field_title">Reference</td>
		<td class="field_value"><c:out value="${ gem.reference }" /></td>
	</tr>
	<tr>
		<td class="field_title">Species</td>
		<td class="field_value"><c:out value="${ applicationScope.arraySpecies[gem.species] }" /></td>
	</tr>
	<tr>
		<td class="field_title">Color</td>
		<td class="field_value"><c:out value="${ gem.color }" /></td>
	</tr>
	<tr>
		<td class="field_title">Shape</td>
		<td class="field_value"><c:out value="${ applicationScope.arrayShape[gem.shape] }" /></td>
	</tr>
	<tr>
		<td class="field_title">Cut</td>
		<td class="field_value"><c:out value="${ applicationScope.arrayCut[gem.cut] }" /></td>
	</tr>
	<tr>
		<td class="field_title">Mass</td>
		<td class="field_value">
			<c:if test="${ !empty gem.mass }">
				<c:out value="${ gem.mass }" /> cts
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="field_title">Width (X)</td>
		<td class="field_value"><c:out value="${ gem.sizeX }" /> mm</td>
	</tr>
	<tr>
		<td class="field_title">Height (Y)</td>
		<td class="field_value"><c:out value="${ gem.sizeY }" /> mm</td>
	</tr>
	<tr>
		<td class="field_title">Depth (Z)</td>
		<td class="field_value">
			<c:if test="${ !empty gem.sizeZ }">
				<c:out value="${ gem.sizeZ }" /> mm
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="field_title">Clarity</td>
		<td class="field_value"><c:out value="${ applicationScope.arrayClarity[gem.clarity] }" /></td>
	</tr>
	<tr>
		<td class="field_title">Enhancement</td>
		<td class="field_value"><c:out value="${ applicationScope.arrayEnhancement[gem.enhancement] }" /></td>
	</tr>
	<tr>
		<td class="field_title">Origin</td>
		<td class="field_value"><c:out value="${ applicationScope.arrayOrigin[gem.origin] }" /></td>
	</tr>
	<tr>
		<td class="field_title">Certificate</td>
		<td class="field_value"><c:out value="${ applicationScope.arrayCertificate[gem.certificate] }" /></td>
	</tr>
	<tr>
		<td class="field_title">Comments</td>
		<td class="field_value"><c:out value="${ gem.comments }" /></td>
	</tr>
	<tr>
		<td class="field_title">Price</td>
		<td class="field_value">
			<c:if test="${ !empty gem.priceValue }">
				<c:out value="${ gem.priceValue } ${ applicationScope.arrayCurrency[gem.priceCurrency] }" /> / cts
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="field_title">Supplier ID</td>
		<td class="field_value"><c:out value="${ gem.supplierID }" /></td>
	</tr>
</table>

</div>