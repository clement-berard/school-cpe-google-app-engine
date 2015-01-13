
$( document ).ready(function() {
	readyToListPlanFromCat();
});


function readyToListPlanFromCat(){
	
	var cat = decodeURIComponent(getParameter('categorie'));
	if(cat != 0){
		var term_search = '{"categorie" : "'+cat+'"}';
		console.log("la categorie : "+term_search);
		ajaxSport.entrainement("getListPlanFromCat",term_search,getListPlanCatCallback,getListPlanCatCallbackError,getListPlanCatCallbackLoader);
		$('#sport_traning_cat_term').html('').html(cat);
	}
	else{
		alert('Erreur, pas de categorie selectionnee');
		window.location = 'ha-search-screen.html';
	}
	
	
}

/**
 * Callbacks pour la liste des plans en fonction d'une categorie
 * @param data
 */
function getListPlanCatCallback(data){
	var size = Object.keys(data).length;
	console.log("taille : "+size);
	if(size == 0){
		$('#alert_for_no_plan').show();
	}
	else{
		var table = $('#id_table_result');
		$.each( data, function( key, value ) {
			value = JSON.parse(value);
			table.append('<tr><td>'+value.title+'</td><td><a class="btn btn-default" href="#">View this plan</a></td></tr>');
		});
		table.show();
	}
}

function getListPlanCatCallbackError(){}
function getListPlanCatCallbackLoader(){}