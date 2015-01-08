var ajaxSport = new ajaxSport();

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
	
	
	// quand on clique sur le bouton rechercher
	$( "#form_search_everything" ).submit(function( event ) {
		event.preventDefault();
		var exos = $('#sport_traning_search_exos');
		var plans = $('#sport_traning_search_plan');
		var input_s = $('#search_value_input');
		var term_title = $('#sport_traning_search_term');
		$('.content_frame').hide();
		$('#sport_traning_search_news').html('');
		// pour les news
		ajaxSport.newsrss(getFeedsCallback,getFeedsCallbackError,getFeedsCallbackLoader);
		// plans and results
		if(input_s.val() == ''){
			exos.html('').html('<span style="color:red">No search term</span>');
			plans.html('').html('<span style="color:red">No search term</span>');
			term_title.html('').html('nothing');
		}
		else{
			term_title.html('').html(input_s.val());
		}

		$('#sport_traning_search').fadeIn();

	});
});


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


