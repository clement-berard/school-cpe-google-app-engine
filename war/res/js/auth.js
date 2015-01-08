var divBtnConnexion = $('#btn_de_connexion');
var divBtnInfosUser = $('#infos_utilisateur');
var InfosUserName = $('#infos_utilisateur_name');
var InfosUserLinkLogout = $('#infos_utilisateur_link_logout');

$( document ).ready(function() {
	$('.openidbtn').on('click',function(){
		event.preventDefault();
		ajaxOpenId($(this).data('provider'),null,callNotConnected);
	});

	InfosUserLinkLogout.on("click",function(){

		event.preventDefault();
		$.ajax({
			type: "POST",
			dataType : 'html',
			url: "../compte",
			data : {method : 'logout'},
			async : false,
			beforeSend: function( xhr ) {
				InfosUserLinkLogout.hide();
				InfosUserName.html('').html('Wait...')
			}
		})
		.done(function( data ) {
			window.location = InfosUserLinkLogout.attr('href');
		});



	});


	ajaxOpenId(null,'isConnected',callbackIsConnected);
});

function callNotConnected(data){
	window.location = data.url;
}

function ajaxOpenId(type,action,callback) {
	var u = window.location.pathname;
	$.ajax({
		type: "GET",
		dataType : "json",
		url: '/compte',
		data : {type:type,action:action,url:u},
	})
	.success(function( data,status ) {
		callback(data);
	})
	.error(function( data,status ) {
		alert('error');
	});
}

function callbackIsConnected(data){
	if(data.is == 1){
		divBtnConnexion.hide();
		divBtnInfosUser.show();
		ajaxOpenId(null,'info',callbackInfo);
	}
}

function callbackInfo(data){
	InfosUserName.html(data.name);
	InfosUserLinkLogout.attr('href',data.logout);
}
