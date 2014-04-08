<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="gem" scope="request" value="${ requestScope.currentGem }" />
<c:set var="formGem" scope="request" value="${ requestScope.form }" />

<c:set var="arraySpecies" scope="request" value='${ 
					["---","Agate","Alexandrite","Almandine","Amazonite","Amber","Amethyst","Ametrine","Andalusite",
                    "Apatite","Aquamarine","Beryl","Bixbite","Chalcedony","Chrome tourmaline","Chrysoberyl",
                    "Citrine","Color change garnet","Color change sapphire","Cubic zirconia","Demantoid",
                    "Diamond","Diopside","Emerald","Fluorite","Goshenite","Hessonite","Iolite","Kunzite",
                    "Kyanite","Lapis lazuli","Malaia garnet","Moonstone","Morganite","Opal","Peridot",
                    "Pezzottaite","Prehnite","Pyrope","Rhodochrosite","Rhodolite","Rhodonite","Rose quartz",
                    "Rubelite","Ruby","Sapphire","Scapolite","Smoky quartz","Spessartite","Sphene","Spinel",
                    "Star ruby","Star sapphire","Tanzanite","Topaz","Tourmaline","Tsavorite","Turquoise","Zircon"] }'/>
                    
<c:set var="arrayShape" scope="request" value='${ 
					["---","Camer","Cushion","Fancy","Heart","Marquise","Octagon","Oval","Pear",
	               "Rectangle","Round","Ruff","Square","Trillion"] }' />

<c:set var="arrayCut" scope="request" value='${ 
					["---","Asscher","Baguette","Briolette","Cabashian","Checker-board","Concave","Diamond",
                	"Fancy","Millenium","Mixte","Portugueese","Princess","Radiant","Ruff","Step-cut"] }' />

<c:set var="arrayClarity" scope="request" value='${
					["---","Loupe Clean","Eye Clean","Eyes Clean to Slightly Included","Slightly Included",
                    "Moderately Included","Heavily Included","Translucent","Opaque"] }' />

<c:set var="arrayEnhancement" scope="request" value='${
					["---","High Pressure","High Temperature","Unknown"] }' />

<c:set var="arrayOrigin" scope="request" value='${
					["---","Madagascar","some country (will add a list)"] }' />

<c:set var="arrayCertificate" scope="request" value='${
					["---", "GIA (Gemological Institut of America)","G�blin Gem Lab",
                    "AIGS (Asian Institute Of Gemological Sciences)","HRD Antwerp"] }' />

<c:set var="arrayCurrency" scope="request" value='${
					["---","EUR","USD","CAD","RUR","UAH"] }' />

<c:set var="arrayLight" scope="request" value='${
					["---","Daylight","Fluorescent light","Incandescent light"] }' />
       
<c:set var="actionServlet" value="${ requestScope.action }" />       
<form method="post" action="<c:url value="${ actionServlet }"/>" accept-charset="UTF-8" name="GemForm" id="GemForm"  enctype="multipart/form-data">
	<h1 class = "maintitle">
	<c:choose>
		<c:when test="${ requestScope.action == '/createGem' }">
			Create a new Gem-ID
		</c:when>
		<c:otherwise>
			Edit a Gem-ID #${ gem.id }
		</c:otherwise>
	</c:choose>
	</h1>
	
	<table id="createGemTable">
		<tr>
			<td class="field_title">
				<span class="required">*</span> Gem species
			</td>
			<td class="field_value">
				<select id="gemSpeciesSelect" name="gemSpecies" tabindex="1">
					<c:forEach var="species" items="${ requestScope.arraySpecies }" varStatus="loop">
						<option value="${ loop.index }" <c:if test="${ loop.index == gem.species }">selected</c:if>>
						<!--<c:choose>
							<c:when test="${ loop.index == gem.species }">
								<option value="${ loop.index }" selected="selected">
							</c:when>
							<c:otherwise>
								<option value="${ loop.index }">
							</c:otherwise>
						</c:choose>-->
							${ species }
						</option>
					</c:forEach>
				</select>
			<span class="error">${ requestScope.form.errors['gemSpecies'] }</span>
			</td>	
		</tr>
		<tr>
			<td class="field_title">
				<span class="required">*</span> Color reference
			</td>
			<td class="field_value">
				<input type="text" id="gemColor" name="gemColor" placeholder="color code" 
					value="<c:out value="${ gem.color }"/>" maxlength="20" tabindex="2"> 
				<span class="error">${ requestScope.form.errors['gemColor'] }</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				<span class="required">*</span> Shape
			</td>
			<td class="field_value">
				<select id="gemShapeSelect" name="gemShape" tabindex="3">
					<c:forEach var="shape" items="${ requestScope.arrayShape }" varStatus="loop">
						<c:choose>
							<c:when test="${ loop.index == gem.shape }">
								<option value="${ loop.index }" selected="selected">
							</c:when>
							<c:otherwise>
								<option value="${ loop.index }">
							</c:otherwise>
						</c:choose>
							${ shape }
						</option>
					</c:forEach>
				</select>
				<span class="error">${ requestScope.form.errors['gemShape'] }</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				<span class="required">*</span> Cut
			</td>
			<td class="field_value">
				<select id="gemCutSelect" name="gemCut" tabindex="4">
					<c:forEach var="cut" items="${ requestScope.arrayCut }" varStatus="loop">
						<c:choose>
							<c:when test="${ loop.index == gem.cut }">
								<option value="${ loop.index }" selected="selected">
							</c:when>
							<c:otherwise>
								<option value="${ loop.index }">
							</c:otherwise>
						</c:choose>
							${ cut }
						</option>
					</c:forEach>
				</select>
				<span class="error">${ requestScope.form.errors['gemCut'] }</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				<span class="required">*</span> Mass
			</td>
			<td class="field_value">
				<input type="text" id="gemMass" name="gemMass" placeholder="mass in cts" 
					value="<c:out value="${ gem.mass }"/>" maxlength="10" tabindex="5"> 
				<span class="error">${ requestScope.form.errors['gemMass'] }</span>		
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<fieldset>
					<legend>Dimensions</legend>
					<table>
						<tr>
							<td class="field_title">
								<span class="required">*</span> Width (x)
							</td>
							<td class="field_value">
								<input type="text" class="dimensions" id="gemSizeX" name="gemSizeX" placeholder="mm"
								value="<c:out value="${ gem.sizeX }"/>" maxlength="10" tabindex="6">
								<span class="error">${ requestScope.form.errors['gemSizeX'] }</span>
							</td>		
						</tr>
						<tr>
							<td class="field_title">
								<span class="required">*</span> Height (y)
							</td>
							<td class="field_value">
								<input type="text" class="dimensions" id="gemSizeY" name="gemSizeY" placeholder="mm" 
								value="<c:out value="${ gem.sizeY }"/>" maxlength="10" tabindex="7">
								<span class="error">${ requestScope.form.errors['gemSizeY'] }</span>
							</td>		
						</tr>
						<tr>
							<td class="field_title">
								Depth (z)
							</td>
							<td class="field_value">
								<input type="text" class="dimensions" id="gemSizeZ" name="gemSizeZ" placeholder="mm"
	 							value="<c:out value="${ gem.sizeZ }"/>" maxlength="10" tabindex="8"><br>
	 							<span class="error">${ requestScope.form.errors['gemSizeZ'] }</span>
							</td>		
						</tr>
					</table>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				<span class="required">*</span> Clarity
			</td>
			<td class="field_value">
				<select id="gemClaritySelect" name="gemClarity" tabindex="9">
					<c:forEach var="clarity" items="${ requestScope.arrayClarity }" varStatus="loop">
						<c:choose>
							<c:when test="${ loop.index == gem.clarity }">
								<option value="${ loop.index }" selected="selected">
							</c:when>
							<c:otherwise>
								<option value="${ loop.index }">
							</c:otherwise>
						</c:choose>
							${ clarity }
						</option>
					</c:forEach>
				</select>
				<span class="error">${ requestScope.form.errors['gemClarity'] }</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Enhancement
			</td>
			<td class="field_value">
				<select id="gemEnhancementSelect" name="gemEnhancement" multiple  tabindex="10">
					<c:forEach var="enhancement" items="${ requestScope.arrayEnhancement }" varStatus="loop">
						<c:choose>
							<c:when test="${ loop.index == gem.enhancement }">
								<option value="${ loop.index }" selected="selected">
							</c:when>
							<c:otherwise>
								<option value="${ loop.index }">
							</c:otherwise>
						</c:choose>
							${ enhancement }
						</option>
					</c:forEach>
				</select>
				<span class="error">${ requestScope.form.errors['gemEnhancement'] }</span>			
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Origin	
			</td>
			<td class="field_value">
				<select id="gemOriginSelect" name="gemOrigin" tabindex="11">
					<c:forEach var="origin" items="${ requestScope.arrayOrigin }" varStatus="loop">
						<c:choose>
							<c:when test="${ loop.index == gem.origin }">
								<option value="${ loop.index }" selected="selected">
							</c:when>
							<c:otherwise>
								<option value="${ loop.index }">
							</c:otherwise>
						</c:choose>
							${ origin }
						</option>
					</c:forEach>
				</select>
				<span class="error">${ requestScope.form.errors['gemOrigin'] }</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Certificate	
			</td>
			<td class="field_value">
				<select id="gemCertificateSelect" name="gemCertificate" tabindex="12">
					<c:forEach var="certificate" items="${ requestScope.arrayCertificate }" varStatus="loop">
						<c:choose>
							<c:when test="${ loop.index == gem.certificate }">
								<option value="${ loop.index }" selected="selected">
							</c:when>
							<c:otherwise>
								<option value="${ loop.index }">
							</c:otherwise>
						</c:choose>
							${ certificate }
						</option>
					</c:forEach>
				</select>
				<span class="error">${ requestScope.form.errors['gemCertificate'] }</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Comments	
			</td>
			<td class="field_value">
				<textarea id="gemComments" name="gemComments" rows="5" cols="40" tabindex="13"> 
				<c:out value="${ gem.comments }"/>
				</textarea>
				<span class="error">${ requestScope.form.errors['gemComments'] }</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Price	
			</td>
			<td class="field_value">
				<input type="text" id="gemPriceValue" name="gemPriceValue" placeholder="for 1 cts" 
					value="<c:out value="${ gem.priceValue }"/>" maxlength="10" tabindex="14">
				<select id="gemPriceCurrencySelect" name="gemPriceCurrency" tabindex="15">
					<c:forEach var="currency" items="${ requestScope.arrayCurrency }" varStatus="loop">
						<c:choose>
							<c:when test="${ loop.index == gem.priceCurrency }">
								<option value="${ loop.index }" selected="selected">
							</c:when>
							<c:otherwise>
								<option value="${ loop.index }">
							</c:otherwise>
						</c:choose>
							${ currency }
						</option>
					</c:forEach>
				</select>	
				<span class="error">${ requestScope.form.errors['gemPrice'] }</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Supplier ID	
			</td>
			<td class="field_value">
				<select id="gemSupplierID" name="gemSupplierID" value="${ gem.supplierID }" tabindex="16">
					<option value="1">1</option>
				</select> 
				<span class="error">
					${ requestScope.form.errors['gemSupplierID'] }
				</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Photo
			</td>
			<td class="field_value">
				<input type="text" id="gemPhotoLink" name="gemPhotoLink" disabled value="${ gem.photoLink }" tabindex="17"> 
				<span class="error">
					${ requestScope.form.errors['gemPhotoLink'] }
				</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Light
			</td>
			<td class="field_value">
				<select id="gemLightSelect" name="gemLight" tabindex="18">
					<c:forEach var="light" items="${ requestScope.arrayLight }" varStatus="loop">
						<c:choose>
							<c:when test="${ loop.index == gem.light }">
								<option value="${ loop.index }" selected="selected">
							</c:when>
							<c:otherwise>
								<option value="${ loop.index }">
							</c:otherwise>
						</c:choose>
							${ light }
						</option>
					</c:forEach>
				</select> 
				<span class="error">
					${ requestScope.form.errors['gemLight'] }
				</span>
			</td>
		</tr>
	
	</table>

	<div class="buttons">
		<input class="button" type="submit" value="Submit" tabindex="19"/>
		<input class="button" type="reset" value="Reset" tabindex="20"/>
	</div>
	
	<p class="error">
		${ requestScope.form.errors['sql'] }
	</p>
	<p class="info">
		${ requestScope.form.result }
	</p>
</form>