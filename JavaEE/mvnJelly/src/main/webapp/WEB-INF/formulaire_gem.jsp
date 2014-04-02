<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="post" action="<c:url value="/createGem"/>" accept-charset="UTF-8" name="GemForm" id="GemForm">
	<h1 class = "maintitle">
		Create a new Gem-ID
	</h1>
	
	<table id="createGemTable">
		<tr>
			<td class="field_title">
				<span class="required">*</span> Gem species
			</td>
			<td class="field_value">
				<select id="gemSpecies" name="gemSpecies" value="${ requestScope.currentGem.species }" tabindex="1">
		<option value=""></option>
		<option value="Agate">Agate</option>
		<option value="Alexandrite">Alexandrite</option>
		<option value="Almandine">Almandine</option>
		<option value="Amazonite">Amazonite</option>
		<option value="Amber">Amber</option>
		<option value="Amber">Amethyst</option>
		<option value="Ametrine">Ametrine</option>
		<option value="Ametrine">Andalusite</option>
		<option value="Apatite">Apatite</option>
		<option value="Aquamarine">Aquamarine</option>
		<option value="Beryl">Beryl</option>
		<option value="Bixbite">Bixbite</option>
		<option value="Chalcedony">Chalcedony</option>
		<option value="Chrome tourmaline">Chrome tourmaline</option>
		<option value="Chrysoberyl">Chrysoberyl</option>
		<option value="Citrine">Citrine</option>
		<option value="Color change garnet">Color change garnet</option>
		<option value="Color change sapphire">Color change sapphire</option>
		<option value="Cubic zirconia">Cubic zirconia</option>
		<option value="Demantoid">Demantoid</option>
		<option value="Diamond">Diamond</option>/**/
		<option value="Diopside">Diopside</option>
		<option value="Emerald">Emerald</option>
		<option value="Fluorite">Fluorite</option>
		<option value="Goshenite">Goshenite</option>
		<option value="Hessonite">Hessonite</option>
		<option value="Iolite">Iolite</option>
		<option value="Kunzite">Kunzite</option>
		<option value="Kyanite">Kyanite</option>
		<option value="Lapis lazuli">Lapis lazuli</option>
		<option value="Malaia garnet">Malaia garnet</option>
		<option value="Moonstone">Moonstone</option>
		<option value="Morganite">Morganite</option>
		<option value="Opal">Opal</option>
		<option value="Peridot">Peridot</option>
		<option value="Pezzottaite">Pezzottaite</option>
		<option value="Prehnite">Prehnite</option>
		<option value="Pyrope">Pyrope</option>
		<option value="Rhodochrosite">Rhodochrosite</option>
		<option value="Rhodolite">Rhodolite</option>
		<option value="Rhodonite">Rhodonite</option>
		<option value="Rose quartz">Rose quartz</option>
		<option value="Rubelite">Rubelite</option>
		<option value="Ruby">Ruby</option>
		<option value="Sapphire">Sapphire</option>
		<option value="Scapolite">Scapolite</option>
		<option value="Smoky quartz">Smoky quartz</option>
		<option value="Spessartite">Spessartite</option>
		<option value="Sphene">Sphene</option>
		<option value="Spinel">Spinel</option>
		<option value="Star ruby">Star ruby</option>
		<option value="Star sapphire">Star sapphire</option>
		<option value="Tanzanite">Tanzanite</option>
		<option value="Topaz">Topaz</option>
		<option value="Tourmaline">Tourmaline</option>
		<option value="Tsavorite">Tsavorite</option>
		<option value="Turquoise">Turquoise</option>
		<option value="Zircon">Zircon</option>
		<option value="other">other</option>
			</select>
			<span class="error">
				${ requestScope.form.errors['gemSpecies'] }
			</span>
			</td>	
		</tr>
		<tr>
			<td class="field_title">
				<span class="required">*</span> Color reference
			</td>
			<td class="field_value">
				<input type="text" id="gemColor" name="gemColor" placeholder="color code" 
					value="<c:out value="${ requestScope.currentGem.color }"/>" maxlength="20" tabindex="2"> 
				<span class="error">
					${ requestScope.form.errors['gemColor'] }
				</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				<span class="required">*</span> Shape
			</td>
			<td class="field_value">
				<select id="gemShape" name="gemShape" value="${ requestScope.currentGem.shape }" tabindex="3">
					<option value=""></option>
					<option value="Camer">Camer</option>
					<option value="Cushion">Cushion</option>	
					<option value="Fancy">Fancy</option>
					<option value="Heart">Heart</option>
					<option value="Marquise">Marquise</option>
					<option value="Octagon">Octagon</option>
					<option value="Oval">Oval</option>
					<option value="Pear">Pear</option>
					<option value="Rectangle">Rectangle</option>
					<option value="Round">Round</option>
					<option value="Ruff">Ruff</option>
					<option value="Square">Square</option>
					<option value="Trillion">Trillion</option>
				</select>
				<span class="error">
					${ requestScope.form.errors['gemShape'] }
				</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				<span class="required">*</span> Cut
			</td>
			<td class="field_value">
				<select id="gemCut" name="gemCut" value="${ requestScope.currentGem.cut }" tabindex="4">
					<option value=""></option>
					<option value="Asscher">Asscher</option>
					<option value="Baguette">Baguette</option>
					<option value="Briolette">Briolette</option>
					<option value="Cabashian">Cabashian</option>
					<option value="Checker-board">Checker-board</option>
					<option value="Concave">Concave</option>
					<option value="Diamond">Diamond</option>
					<option value="Fancy">Fancy</option>
					<option value="Millenium">Millenium</option>
					<option value="Mixte">Mixte</option>
					<option value="Portugueese">Portugueese</option>
					<option value="Princess">Princess</option>
					<option value="Radiant">Radiant</option>
					<option value="Ruff">Ruff</option>
					<option value="Step-cut">Step-cut</option>**/**/
				</select>
				<span class="error">
					${ requestScope.form.errors['gemCut'] }
				</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Mass
			</td>
			<td class="field_value">
				<input type="text" id="gemMass" name="gemMass" placeholder="mass in cts" 
					value="<c:out value="${ requestScope.currentGem.mass }"/>" maxlength="10" tabindex="5"> 
				<span class="error">
					${ requestScope.form.errors['gemMass'] }
				</span>		
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
								value="<c:out value="${ requestScope.currentGem.sizeX }"/>" maxlength="10" tabindex="6">
								<span class="error">
									${ requestScope.form.errors['gemSizeX'] }
								</span>
							</td>		
						</tr>
						<tr>
							<td class="field_title">
								<span class="required">*</span> Height (y)
							</td>
							<td class="field_value">
								<input type="text" class="dimensions" id="gemSizeY" name="gemSizeY" placeholder="mm" 
								value="<c:out value="${ requestScope.currentGem.sizeY }"/>" maxlength="10" tabindex="7">
								<span class="error">
									${ requestScope.form.errors['gemSizeY'] }
								</span>
							</td>		
						</tr>
						<tr>
							<td class="field_title">
								Depth (z)
							</td>
							<td class="field_value">
								<input type="text" class="dimensions" id="gemSizeZ" name="gemSizeZ" placeholder="mm"
	 							value="<c:out value="${ requestScope.currentGem.sizeZ }"/>" maxlength="10" tabindex="8"><br>
	 							<span class="error">
									${ requestScope.form.errors['gemSizeZ'] }
								</span>
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
				<select id="gemClarity" name="gemClarity" value="${ requestScope.currentGem.clarity }" tabindex="9">
					<option value=""></option>
					<option value="Loupe Clean">Loupe Clean</option>
					<option value="Eye Clean">Eye Clean</option>
					<option value="Eyes Clean to Slightly Included">Eyes Clean to Slightly Included</option>
					<option value="Slightly Included">Slightly Included</option>
					<option value="Moderately Included">Moderately Included</option>
					<option value="Heavily Included">Heavily Included</option>
					<option value="Translucent">Translucent</option>
					<option value="Opaque">Opaque</option>
				</select>
				<span class="error">
					${ requestScope.form.errors['gemClarity'] }
				</span>
					
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Enhancement
			</td>
			<td class="field_value">
				<!-- TO DO: add a possibility to choose several enhancements. -->
				<select id="gemEnhancement" name="gemEnhancement" value="${ requestScope.currentGem.enhancement }" tabindex="10">
					<option value=""></option>
					<option value="HP">HP (High Pressure)</option>
					<option value="HT">HT (High Temperature)</option>
					<option value="Unknown">Unknown</option>
				</select>
				<span class="error">
					${ requestScope.form.errors['gemEnhancement'] }
				</span>			
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Origin	
			</td>
			<td class="field_value">
				<select id="gemOrigin" name="gemOrigin" value="${ requestScope.currentGem.origin }" tabindex="11">
					<option value=""></option>
					<option value="Madagascar">Madagascar</option>
				</select>
				<span class="error">
					${ requestScope.form.errors['gemOrigin'] }
				</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Certificate	
			</td>
			<td class="field_value">
				<select id="gemCertificate" name="gemCertificate" value="${ requestScope.currentGem.certificate }" tabindex="12">
					<option value=""></option>
					<option value="GIA">GIA (Gemological Institute of America)</option>
					<option value="AIGS">AIGS (Asian Institute of Gemological Sciences)</option>
					<option value="Güblin">Güblin</option>
					<option value="HRD">HRD</option>
				</select>
				<span class="error">
					${ requestScope.form.errors['gemCertificate'] }
				</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Comments	
			</td>
			<td class="field_value">
				<textarea id="gemComments" name="gemComments" rows="5" cols="40" tabindex="13"> 
				<c:out value="${ requestScope.currentGem.comments }"/>
				</textarea>
				<span class="error">
					${ requestScope.form.errors['gemComments'] }
				</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Price	
			</td>
			<td class="field_value">
				<input type="text" id="gemPriceValue" name="gemPriceValue" placeholder="for 1 cts" 
					value="<c:out value="${ requestScope.currentGem.priceValue }"/>" maxlength="10" tabindex="14">
				<select id="gemPriceCurrency" name="gemPriceCurrency" value="${ requestScope.currentGem.priceCurrency }" tabindex="15">
					<option value="">Currency</option>
					<option value="EUR">EUR</option>
					<option value="USD">USD</option>
				</select>	
				<span class="error">
					${ requestScope.form.errors['gemPrice'] }
				</span>
			</td>
		</tr>
		<tr>
			<td class="field_title">
				Supplier ID	
			</td>
			<td class="field_value">
				<select id="gemSupplierID" name="gemSupplierID" value="${ requestScope.currentGem.supplierID }" tabindex="16">
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
				<input type="text" id="gemPhotoLink" name="gemPhotoLink" disabled value="${ requestScope.currentGem.photoLink }" tabindex="17"> 
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
				<select id="gemLight" name="gemLight" value="${ requestScope.currentGem.light }" tabindex="18">
					<option value="">Photo taken under:</option>
					<option value="Daylight">Daylight</option>
					<option value="Fluorescent light">Fluorescent light</option>
					<option value="Incandescent light">Incandescent light</option>
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