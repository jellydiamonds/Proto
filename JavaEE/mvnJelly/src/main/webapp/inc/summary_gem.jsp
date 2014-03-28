<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p class="info">${ requestScope.form.result }</p>

<table id="recap">
	<tr>
		<td class="caption">Gem #:</td>
		<td class="value"><c:out value="${ requestScope.currentGem.id }" /></td>
	</tr>
	<tr>
		<td class="caption">Type:</td>
		<td class="value"><c:out value="${ requestScope.currentGem.type }" /></td>
	</tr>
	<tr>
		<td class="caption">Color:</td>
		<td class="value"><c:out value="${ requestScope.currentGem.color }" /></td>
	</tr>
</table>