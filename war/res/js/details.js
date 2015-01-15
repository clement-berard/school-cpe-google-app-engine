var nbExo = 0;
var durByEx = new Array();
var clock = new Array();
var callnumber = 1;
$(document).ready(function() { 

	var aS = new ajaxSport(); 
	var action = "details";
	var data ;

	if(decodeURIComponent(getParameter('plan')) != 0){
		data = '{"plan" : "'+decodeURIComponent(getParameter('plan'))+'"}';	
	} else {
		data = '{"exo" : "'+decodeURIComponent(getParameter('exo'))+'"}';
	}

	aS.entrainement(action, data,callbackSuccess,callbackError,loaderFunc); 

	$("#e1").select2(); 
	date=new Date('5.10.2012 00:20:00');
	time=date.getTime();

	date2=new Date('5.10.2012 00:05:00');
	time2=date2.getTime();

	date3=new Date('5.10.2012 00:25:00');
	time3=date.getTime();


});

function addEventPlayer(){
	jQuery('.btn-play').on('click',function(){

		idCompteur = $(this).data('idexo');
		clock[idCompteur].start();

	});

	jQuery('.btn-pause').on('click',function(){

		idCompteur = $(this).data('idexo');
		clock[idCompteur].stop();

	});

	jQuery('.btn-stop').on('click',function(){
		idCompteur = $(this).data('idexo');
		if(clock[idCompteur+1]){
			if (confirm("Hey ! Il te reste que "+clock[idCompteur].getTime()+" secondes ?? Tu-veux arreter l'exercice et passer au suivant ?")) { 
				clock[idCompteur].stop();
				clock[idCompteur+1].start();
			}
		}
		else{
			if (confirm("Hey ! Il te reste que "+clock[idCompteur].getTime()+" secondes ?? En plus t'as fini l'entrainement ! Tu veux tout arreter  ?")) { 
				clock[idCompteur].stop();
			}
		}
	});

	jQuery('.btn-reset').on('click',function(){

//		idCompteur = $(this).data('idexo');
//		clock[idCompteur].flip();

	});
}




function callbackSuccess(data){
	console.log(data);
	//alert(data[0].title);

	//alert(data.length);

	addExercice(data);

	var j=0;
	var id;
	var dur;
	var dateFormat = new Array();
	var date = new Array();
	var time = new Array();
	for(j=1; j<nbExo+1; j++){
		id = "#flipcountdownbox" + j;
		dur =  durByEx[j];

		console.log(j);
		clock[j] = $(id).FlipClock($(id).attr('rel'), {
			clockFace: 'MinuteCounter',
			countdown: true,
			autoStart :false,
			callbacks: {
				stop: function() {
					callbackStop();
				}
			}
		});

		
		



		dur=0;
	}
	addEventPlayer();
}

function callbackStop(id){
	
	if(callnumber != nbExo){
		callnumber++;
		clock[callnumber].start();
	}else{
		alert("fin");
	}
	
	
}

function callbackError(){}
function loaderFunc(){}

function addExercice(data){
	var elt;
	var title, duration, description, durationSec;

	$.each(data, function( key, value ) {
		title = data[key]["title"];
		duration = data[key]["duration"];
		description = data[key]["description"];
		durationSec = data[key]["durationSec"];
		nbExo++;  	
		durByEx[nbExo] = durationSec;
		$("#add").append('<tr><td class=" col-md-12 col-sm-12 col-xs-12"><div class="row"><div class=" col-md-3 col-sm-12 col-xs-12 "><h3>' + title +' </h3></div>' +
				'<div class=" col-md-1 col-sm-2 col-xs-2 ">' +
				'<p id="totalTimeValue" style="margin-top:25px"><span class="glyphicon glyphicon-time"></span> ' + duration + '</p>'+
				'</div>'+
				'</div>'+
				'<div class="row">'+
				'<div class=" col-md-1 col-sm-0 col-xs-0 " ></div>'+
				'<div class=" col-md-6 col-sm-12 col-xs-12 ">'+
				'<p>' + description + '.</p>'+
				'</div>'+
				'<div class=" col-md-3 col-sm-12 col-xs-12 ">'+
				'<div class=" col-md-12 col-sm-12 col-xs-12 ">'+
				'<div id="flipcountdownbox'+nbExo+'" rel="'+durationSec+'" data-idexo="'+nbExo+'" class="clock" style="margin:2em;width:400px">'+                            
				'</div>'+
				'</div>'+
				'<div class=" col-md-12 col-sm-12 col-xs-12 centered">'+
				'<div class="btn-group">'+
				'<button type="button" class="btn btn-default btn-play" data-idexo="'+nbExo+'" ><span class="glyphicon glyphicon-play"></span> </button>'+
				'<button type="button" class="btn btn-default btn-pause" data-idexo="'+nbExo+'"> <span class="glyphicon glyphicon-pause"></span> </button>'+
				'<button type="button" class="btn btn-default btn-stop" data-idexo="'+nbExo+'"> <span class="glyphicon glyphicon-stop"></span> </button>'+
				'<button type="button" class="btn btn-default btn-reset" data-idexo="'+nbExo+'" > <span class="glyphicon glyphicon-repeat"></span> </button>'+
				'</div>'+

				'</div>'+

				'</div>'+
				'</div>'+
				'<div class=" col-md-2 ol-sm-5 col-xs-12 text-center" >'+
				'<button type="submit" class="btn btn-success btn-lg" disabled="disabled"> <span class="glyphicon glyphicon-ok"></span> </button>'+    
				'<button type="submit" class="btn btn-danger btn-sm" disabled="disabled"> <span class="glyphicon glyphicon-fast-forward"></span> </button>'+
				'</div>'+

				'</td>'+
		'</tr>');
		//});

	});
}

function dateToDatetime(sec){

	date = new Date();
	console.log(date.getSeconds());
	date.setSeconds(date.getSeconds() + sec);
	date = date.getFullYear() + '/' +
	('00' + (date.getMonth()+1)).slice(-2) + '/' +
	('00' + date.getDate()).slice(-2) + ' ' + 
	('00' + date.getHours()).slice(-2) + ':' + 
	('00' + date.getMinutes()).slice(-2) + ':' + 
	('00' + date.getSeconds()).slice(-2);
	console.log(date);
	return date;

}
