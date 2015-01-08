var ajaxSport = new ajaxSport();

function callbackSuccessItemsSports(data){
	console.log(data);
	$('#content_sports').fadeIn();
	var size = Object.keys(data).length;
//	var size = parseInt(5);
	var nb_item_per_line = 4;
	var nbLigne = Math.ceil(size/nb_item_per_line);
	if(size != 0){
		// ajout des lignes
		for(i = 1;i <= nbLigne;i++){
			$('<div/>', {id: 'id_sport_line_'+i,class : 'col-md-12',style : 'margin-bottom: 1%'}).appendTo('#content_sports');
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
//		$('<div/>', {
//		id: 'foo',
//		href: 'http://google.com',
//		title: 'Become a Googler',
//		rel: 'external',
//		text: 'Go to Google!'
//		}).appendTo('#content_sports');
	}


}

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
		$('.content_frame').hide();
		$('#sport_traning_search_news').html('');
		//
		ajaxSport.newsrss(getFeedsCallback,getFeedsCallbackError,getFeedsCallbackLoader);


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


