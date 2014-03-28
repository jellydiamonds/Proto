<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<p>
		<label for="gemType">Gem species <span class="required">*</span></label>
		<!-- <input type="text" id="gemType" name="gemType" value="<c:out value="${ requestScope.currentGem.type }"/>" size="20" maxlength="50" />	
		<span class="error">${ requestScope.form.errors['gemType'] }</span> <br/> -->
		<select id="gemType" name="gemType">
			<option value="">...select...</option>
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
			<option value="Diamond">Diamond</option>
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
		
	</p>
	<p>
		<label for="gemColor">Color reference <span class="required">*</span></label>
		<input type="text" id="gemColor" name="gemColor" value="<c:out value="${ requestScope.currentGem.color }"/>" size="20" maxlength="20" /> 
		<span class="error">${ requestScope.form.errors['gemColor'] }</span> <br/>
	</p>
	<p class="error">${ requestScope.form.errors['sql'] }</p>