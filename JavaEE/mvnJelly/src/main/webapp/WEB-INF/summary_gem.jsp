<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p class="info">${ requestScope.form.result }</p>

<div id="gemIDcontainer">
<table id="recapTable">
	<tr>
		<td class="field_title">Gem #</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.id }" /></td>
	</tr>
	<tr>
		<td class="field_title">Date</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.creationDate }" /></td>
	</tr>
	<tr>
		<td class="field_title">Reference</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.reference }" /></td>
	</tr>
	<tr>
		<td class="field_title">Species</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.species }" /></td>
	</tr>
	<tr>
		<td class="field_title">Color</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.color }" /></td>
	</tr>
	<tr>
		<td class="field_title">Shape</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.shape }" /></td>
	</tr>
	<tr>
		<td class="field_title">Cut</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.cut }" /></td>
	</tr>
	<tr>
		<td class="field_title">Mass</td>
		<td class="field_value">
			<c:if test="${ !empty gem.mass }">
				<c:out value="${ requestScope.currentGem.mass }" /> cts
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="field_title">Width (X)</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.sizeX }" /> mm</td>
	</tr>
	<tr>
		<td class="field_title">Height (Y)</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.sizeY }" /> mm</td>
	</tr>
	<tr>
		<td class="field_title">Depth (Z)</td>
		<td class="field_value">
			<c:if test="${ !empty gem.sizeZ }">
				<c:out value="${ requestScope.currentGem.sizeZ }" /> mm
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="field_title">Clarity</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.clarity }" /></td>
	</tr>
	<tr>
		<td class="field_title">Enhancement</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.enhancement }" /></td>
	</tr>
	<tr>
		<td class="field_title">Origin</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.origin }" /></td>
	</tr>
	<tr>
		<td class="field_title">Certificate</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.certificate }" /></td>
	</tr>
	<tr>
		<td class="field_title">Comments</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.comments }" /></td>
	</tr>
	<tr>
		<td class="field_title">Price</td>
		<td class="field_value">
			<c:if test="${ !empty gem.sizeZ }">
				<c:out value="${ requestScope.currentGem.priceValue } ${ requestScope.currentGem.priceValue }" /> / cts
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="field_title">Supplier ID</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.supplierID }" /></td>
	</tr>
	<tr>
		<td class="field_title">Photo link</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.photoLink }" /></td>
	</tr>
	<tr>
		<td class="field_title">Photo taken under</td>
		<td class="field_value"><c:out value="${ requestScope.currentGem.light }" /></td>
	</tr>
</table>
</div>