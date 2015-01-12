var ajaxSport = new ajaxSport();
var input_s = $('#search_value_input');
function getAllListSport(){
	ajaxSport.recherche("getAllSports",{},callbackSuccessItemsSports,callbackError,loaderFunc);
}

function callbackSuccessItemsSports(data){
	console.log(data);
	$('.content_frame').hide();
	$('#content_sports_inner').html('');
	$('#content_sports').fadeIn();
	var size = Object.keys(data).length;
//	var size = parseInt(5);
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
// callback vide par defaut
function callbackError(){}
function loaderFunc(){}


function functionForListSports(){


	$(".btn_sport_detail").on('click',function(){


		alert($(this).attr('rel'));

	});

}


$( document ).ready(function() {
	
	
	$( "#form_search_everything" ).submit(function( event ) {
		event.preventDefault();
		$("#loader_modal").modal("show");
		ajaxOpenId(null,'isConnected',callbackIsConnectedToMakeSearch);
		
	});
	
});


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
		term_title.html('').html(param);
	}

	$('#sport_traning_search').fadeIn();
}


/**
 * Search plan
 *
 */

function getSearchPlanCallback(data){
	console.log(data);
	var ul = $('#sport_traning_search_plan > ul');
	$.each( data, function( key, value ) {
		value = JSON.parse(value);
		ul.append('<li><a href="#">'+value.title+'</a></li>');
	});
}

function getSearchPlanCallbackError(){
	
}

function getSearchPlanCallbackLoader(){
	
}

/**
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
 */

function getParameter(param){
	return location.search.split(param+'=')[1] ? location.search.split(param+'=')[1] : 0;
}



