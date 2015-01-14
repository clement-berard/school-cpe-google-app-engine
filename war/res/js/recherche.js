/**
 * differentes mises en place d'event
 */

$( document ).ready(function() {
	$( "#form_search_everything" ).submit(function( event ) {
		event.preventDefault();
		$("#loader_modal").modal("show");
		ajaxOpenId(null,'isConnected',callbackIsConnectedToMakeSearch);
	});
});


//init de la class ajaxHelper
var ajaxSport = new ajaxSport();
//var glob
var input_s = $('#search_value_input'); // champs input de recherche
/**
 * fonction qui appelle un WS pour avoir la liste de tous les sports
 */
function getAllListSport(){
	ajaxSport.recherche("getAllSports",{},callbackSuccessItemsSports,callbackError,loaderFunc);
}
/**
 * callback success pour la liste des sports
 * on affiche les resultats sur la page avec le code suivant (avec un peu de maths pour avoir des
 * lignes de max 4 elems)
 * @param data
 */
function callbackSuccessItemsSports(data){
	console.log(data);
	$('.content_frame').hide();
	$('#content_sports_inner').html('');
	$('#content_sports').fadeIn();
	var size = Object.keys(data).length;
	var nb_item_per_line = 4;
	var nbLigne = Math.ceil(size/nb_item_per_line);
	if(size != 0){
		// ajout des lignes
		for(i = 1;i <= nbLigne;i++){
			$('<div/>', {id: 'id_sport_line_'+i,class : 'col-md-12',style : 'margin-bottom: 1%'}).appendTo('#content_sports_inner');
		}
		// ajout des sports
		var ligne_en_cours = 1;
		i = 1;
		$.each( data, function( key, value ) {
			$('<button/>', {id: 'id_sport_'+i,class : 'col-md-3 btn btn-default btn-lg btn_sport_detail',text : value,rel : value}).appendTo('#id_sport_line_'+ligne_en_cours);
			if((i % nb_item_per_line) == 0){
				ligne_en_cours++;
			}
			i++;
		});
		functionForListSports();
	}
}
//callback vide par defaut, on peut reutiliser
function callbackError(){}
function loaderFunc(){}
//callback vide par defaut -- end
/**
 * fonction qui va appeller une page pour afficher les plans
 * d'une certaine categorie
 */
function functionForListSports(){
	$(".btn_sport_detail").on('click',function(){
		window.location = 'ha-list-plan-cat.html?categorie='+$(this).attr('rel');
	});
}
/**
 * fonction qui va appeller une page pour afficher les plans
 * d'une certaine categorie
 * 
 * CALLBACKS LIST PLAN CAT
 * 
 */


/**
 * CALLBACKS LIST PLAN CAT -- END
 */





/**
 * callback pour faire la recherche, si et seulement si un user est connecte
 */
function callbackIsConnectedToMakeSearch(data){
	if(data.is == 1){
		window.location = "ha-result-screen.html?term="+encodeURIComponent(input_s.val());	
	}
	else{
		$("#loader_modal").modal("hide");
		alert('Vous devez etre connecte pour faire une recherche !!');
		window.location = "ha-search-screen.html";	
	}
}

/**
 * appeller sur la page des resultats de la recherche
 * appelle 3 WS pour recherche dans exos, plan et affiche les RSS
 * et on affiche tout, grace a different callback (en dessus de cette fonction)
 */
function getResultSearch(){
	var param = decodeURIComponent(getParameter('term'));
	var exos = $('#sport_traning_search_exos');
	var plans = $('#sport_traning_search_plan');
	var term_title = $('#sport_traning_search_term');
	$('.content_frame').hide();
	$('#sport_traning_search_news').html('');
	// pour les news
	ajaxSport.newsrss(getFeedsCallback,getFeedsCallbackError,getFeedsCallbackLoader);
	var term_search = '{"term" : "'+param+'"}';
	ajaxSport.recherche("getSearchPlan",term_search,getSearchPlanCallback,getSearchPlanCallbackError,getSearchPlanCallbackLoader);
	ajaxSport.recherche("getSearchExercices",term_search,getSearchExoCallback,getSearchExoCallbackError,getSearchExoCallbackLoader);
	// plans and results
	if(param == ''){
		exos.html('').html('<span style="color:red">No search term</span>');
		plans.html('').html('<span style="color:red">No search term</span>');
		term_title.html('').html('nothing');
	}
	else{
		term_title.html('').html('"'+param+'"');
	}
	$('#sport_traning_search').fadeIn();
}


/**
 * Callback
 * Search plan
 *
 */

function getSearchPlanCallback(data){
	console.log(data);
	var ul = $('#sport_traning_search_plan > ul');
	$.each( data, function( key, value ) {
		value = JSON.parse(value);
		ul.append('<li><a href="ha-result-detail-screen.html?plan='+value.title+'">'+value.title+'</a></li>');
	});
}

function getSearchPlanCallbackError(){

}

function getSearchPlanCallbackLoader(){

}

/**
 * Callback
 * Search exercice
 *
 */

function getSearchExoCallback(data){
	console.log(data);
	var ul = $('#sport_traning_search_exos > ul');
	$.each( data, function( key, value ) {
		value = JSON.parse(value);
		ul.append('<li><a href="#">'+value.title+'</a></li>');
	});
}

function getSearchExoCallbackError(){

}

function getSearchExoCallbackLoader(){

}


/**
 * Callback
 * FEEDS
 * @param data
 */
function getFeedsCallback(data){

	console.log(data.rss.channel.item);
	var rss = data.rss.channel.item;
	$.each( rss, function( key, value ) {
		$('<a/>', {
			id : "search_link_"+key,
			href: value.link,
			title: value.title,
			rel: 'external',
			text: value.title,
			target : '_blank'
		}).appendTo('#sport_traning_search_news');
		$("#search_link_"+key).append('<br>');

	});

}

function getFeedsCallbackError(){};
function getFeedsCallbackLoader(){};


/**
 * get parameters
 * fonction qui retourne un paramtre en GET qui se trouve dans l'url, 
 * sert pour le term de la recherche entre autre
 * 
 */

function getParameter(param){
	return location.search.split(param+'=')[1] ? location.search.split(param+'=')[1] : 0;
}



