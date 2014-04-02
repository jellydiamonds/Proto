$(document).ready(function(){
	var urlRoot = "api"; /*http://localhost:8080/jellyDiamonds-1.0*/
	var addGemURL = "http://jelly.diamonds/jellyDiamonds-1.0/api/gems/addGem";
	var addGemFinalURL;
	
	$("#helloLink").attr("href", urlRoot + "/hello");
	$("#randomLink").attr("href", urlRoot + "/random");
	
	$("#saveAddGemURL").click(function(){
		var species = $("#inlineGemSpecies").val();
		var color = $("#inlineGemColor").val();
		addGemFinalURL = addGemURL + "/" + species + "/" + color;
		$("#addGemLink").attr("href", addGemFinalURL);
		$("#addGemLink").html(addGemFinalURL);
	});
	
	
	/*
	$("#submitAddGem").click(function(){
		var species = $("#inlineGemSpecies").val();
		var color = $("#inlineGemColor").val();
		var addGemFinalURL = "http://jelly.diamonds/jellyDiamonds-1.1/api/gems/addGem/" + species + "/" + color;

		alert("species: " + species);
		alert("color: " + color);
		alert("addGemFinalURL : " + addGemFinalURL );
		
		$.ajax({
			type: "POST",
			url: addGemFinalURL,
			data: "",
			success: function(){
				alert("Gem added! Check out the database");
			}
		});		
	});*/
});