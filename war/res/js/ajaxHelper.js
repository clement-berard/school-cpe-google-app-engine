function ajaxSport() {


	this.method = "POST",
	this.dataType = "json",
	this.base_url = "http://localhost:8888/",
	this.url_recherche = "recherche",
	this.url_accueil = "accueil",
	this.url_entrainement = "entrainement",
	this.url_compte = "compte",

	/**
	 * 
	 */
	this.recherche = function (action, data,callbackSuccess,callbackError,callLoader) {
		this.defaultAjax(this.url_recherche,action,data,callbackSuccess,callbackError,callLoader);
	},

	this.accueil = function (action, data,callbackSuccess,callbackError,callLoader) {
		this.defaultAjax(this.url_accueil,action,data,callbackSuccess,callbackError,callLoader);
	},

	this.entrainement = function (action, data,callbackSuccess,callbackError,callLoader) {
		this.defaultAjax(this.url_entrainement,action,data,callbackSuccess,callbackError,callLoader);
	},

	this.compte = function (action, data,callbackSuccess,callbackError,callLoader) {
		this.defaultAjax(this.url_compte,action,data,callbackSuccess,callbackError,callLoader);
	},

	this.defaultAjax = function (url,action,data,callbackSuccess,callbackError,callLoader){

//		$.ajax({
//		type: this.method,
//		dataType : this.dataType,
//		url: this.base_url+url,
//		beforeSend: function( xhr ) {

//		}
//		})
//		.success(function( data ) {
//		console.log('recherche success '+action);
//		callbackSuccess();
//		})
//		.error(function( data ) {
//		console.log('recherche error '+action);
//		callbackError();
//		});

		$.ajax({
			type: this.method,
			dataType : this.dataType,
			url: "http://ip.jsontdsest.com/",
			beforeSend: function( xhr ) {
				callLoader();
				$("#loader_modal").modal("show");
			}
		})
		.success(function( data ) {
			console.log('recherche success '+action);
			$("#loader_modal").modal("hide");
			callbackSuccess(data);
		})
		.error(function( data ) {
			console.log('recherche error '+action);
			$("#loader_modal").find('.modal-content').html('<span style="color:red;">HTTP ERROR</span>');
			callbackError(data);
		});
	}




};