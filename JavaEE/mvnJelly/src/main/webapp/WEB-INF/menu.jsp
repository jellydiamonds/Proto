<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
	<p><a href="<c:url value="/addGem"/>">Create a gem</a></p>
	<p><a href="<c:url value="/findGem"/>">Find a gem</a></p>
	<p><a href="<c:url value="/listGems"/>">List of gems</a></p>
</div>

<c:set var="arraySpecies" scope="application" value='${ 
					["---","Agate","Alexandrite","Almandine","Amazonite","Amber","Amethyst","Ametrine","Andalusite",
                    "Apatite","Aquamarine","Beryl","Bixbite","Chalcedony","Chrome tourmaline","Chrysoberyl",
                    "Citrine","Color change garnet","Color change sapphire","Cubic zirconia","Demantoid",
                    "Diamond","Diopside","Emerald","Fluorite","Goshenite","Hessonite","Iolite","Kunzite",
                    "Kyanite","Lapis lazuli","Malaia garnet","Moonstone","Morganite","Opal","Peridot",
                    "Pezzottaite","Prehnite","Pyrope","Rhodochrosite","Rhodolite","Rhodonite","Rose quartz",
                    "Rubelite","Ruby","Sapphire","Scapolite","Smoky quartz","Spessartite","Sphene","Spinel",
                    "Star ruby","Star sapphire","Tanzanite","Topaz","Tourmaline","Tsavorite","Turquoise","Zircon"] }'/>

<c:set var="arrayShape" scope="application" value='${ 
					["---","Camer","Cushion","Fancy","Heart","Marquise","Octagon","Oval","Pear",
	               "Rectangle","Round","Rough","Square","Trillion"] }' />

<c:set var="arrayCut" scope="application" value='${ 
					["---","Asscher","Baguette","Briolette","Cabashian","Checker-board","Concave","Diamond",
                	"Fancy","Millenium","Mixte","Portugueese","Princess","Radiant","Ruff","Step-cut"] }' />

<c:set var="arrayClarity" scope="application" value='${
					["---","Loupe Clean","Eye Clean","Eyes Clean to Slightly Included","Slightly Included",
                    "Moderately Included","Heavily Included","Translucent","Opaque"] }' />

<c:set var="arrayEnhancement" scope="application" value='${
					["---","High Pressure","High Temperature","Unknown"] }' />

<c:set var="arrayOrigin" scope="application" value='${
					["---","Madagascar","some country (will add a list)"] }' />

<c:set var="arrayCertificate" scope="application" value='${
					["---", "GIA (Gemological Institut of America)","Güblin Gem Lab",
                    "AIGS (Asian Institute Of Gemological Sciences)","HRD Antwerp"] }' />

<c:set var="arrayCurrency" scope="application" value='${
					["---","EUR","USD","CAD","RUR","UAH"] }' />

<c:set var="arrayLight" scope="application" value='${
					["---","Daylight","Fluorescent light","Incandescent light"] }' />
                    