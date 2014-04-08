var arraySpecies = ["---","Agate","Alexandrite","Almandine","Amazonite","Amber","Amethyst","Ametrine","Andalusite",
                    "Apatite","Aquamarine","Beryl","Bixbite","Chalcedony","Chrome tourmaline","Chrysoberyl",
                    "Citrine","Color change garnet","Color change sapphire","Cubic zirconia","Demantoid",
                    "Diamond","Diopside","Emerald","Fluorite","Goshenite","Hessonite","Iolite","Kunzite",
                    "Kyanite","Lapis lazuli","Malaia garnet","Moonstone","Morganite","Opal","Peridot",
                    "Pezzottaite","Prehnite","Pyrope","Rhodochrosite","Rhodolite","Rhodonite","Rose quartz",
                    "Rubelite","Ruby","Sapphire","Scapolite","Smoky quartz","Spessartite","Sphene","Spinel",
                    "Star ruby","Star sapphire","Tanzanite","Topaz","Tourmaline","Tsavorite","Turquoise","Zircon"];

var arrayShape = ["---","Camer","Cushion","Fancy","Heart","Marquise","Octagon","Oval","Pear",
	               "Rectangle","Round","Ruff","Square","Trillion"];

var arrayCut = ["---","Asscher","Baguette","Briolette","Cabashian","Checker-board","Concave","Diamond",
                "Fancy","Millenium","Mixte","Portugueese","Princess","Radiant","Ruff","Step-cut"];

var arrayClarity = ["---","Loupe Clean","Eye Clean","Eyes Clean to Slightly Included","Slightly Included",
                    "Moderately Included","Heavily Included","Translucent","Opaque"];

var arrayEnhancement = ["---","High Pressure","High Temperature","Unknown"];

var arrayOrigin = ["---","Madagascar","some country (will add a list)"];

var arrayCertificate = ["---", "GIA (Gemological Institut of America)","Güblin",
                        "AIGS (Asian Institute Of Gemological Sciences)","HRD"];

var arrayCurrency = ["---","EUR","USD","CAD","RUR","UAH"];

var arrayLight = ["---","Daylight","Fluorescent light","Incandescent light"];

$(document).ready(function() {
	// Populating the <select> elements
	//populate( 'gemSpeciesSelect', arraySpecies );
	populate( 'gemShapeSelect', arrayShape );
	populate( 'gemCutSelect', arrayCut );
	populate( 'gemClaritySelect', arrayClarity );
	populate( 'gemEnhancementSelect', arrayEnhancement );
	populate( 'gemOriginSelect', arrayOrigin );
	populate( 'gemCertificateSelect', arrayCertificate );
	populate( 'gemPriceCurrencySelect', arrayCurrency );
	populate( 'gemLightSelect', arrayLight );
});

/* Populates <select> element with <option> elements, 
whose value and text are taken from the string array. */
function populate( selectToPopulate, optionsArray ) {
	var select = document.getElementById(selectToPopulate);
	// Empty <select> content before populating it.
	select.innerHTML = ""; /* OR :  while (modelList.options.length)  {  modelList.remove(0);  }*/
	for (var option in optionsArray) {
		var newOption = document.createElement("option");
		// Index starts from 0, which corresponds to "---". Existing database values start from 1.
		newOption.value = option;
		newOption.innerHTML = optionsArray[option];
		select.options.add( newOption );
	}
}

/* Sets the selected index of a given <select> element */
function setSelection( selectToSetIndex, index ) {
	var select = document.getElementById( selectToSetIndex );
	select.selectedIndex = index;
}