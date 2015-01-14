$(document).ready(function() { $("#e1").select2(); });       

var ajaxSport = new ajaxSport();
ajaxSport.recherche("getAllSports",{},callbackSuccessItemsSports,callbackError,loaderFunc);
function callbackSuccessItemsSports(data){
	console.log(data);
	var le_select = $("#e1");
	
	$.each( data, function( key, value ) {
		le_select.append('<option value="'+value+'">'+value+'</option>');
	});
	
	
}
//callback vide par defaut, on peut reutiliser
function callbackError(){}
function loaderFunc(){}
var table;
var ii=1;
var timeSec=0;

$( "#target" ).submit(function( event ) {
	if(ii != 1){
		var data;
		//var term_search = '{"term" : "'+param+'"}';
		event.preventDefault();
		action = "addPlan";


		//console.log(JSON.stringify($("#target").serializeObject()));
		data = JSON.stringify($("#target").serializeObject());
		console.log(data);

		ajaxSport.entrainement(action, data,callbackSuccess,callbackError,loaderFunc);
	}
});	

function callbackSuccess(data){

	$(".alert").show();
}

function callbackError(){}
function loaderFunc(){}

$(	"#addExercice" ).click(function() {

	var time ='';
	var thisTime='';
	if($("#hour").val()){
		time = $("#hour").val() + 'h ';
		timeSec += parseInt($("#hour").val()*3600);
		thisTime = parseInt($("#hour").val()*3600);
	}
	if($("#min").val()){
		time += $("#min").val() + 'min ';
		timeSec += parseInt($("#min").val()*60);
		thisTime += parseInt($("#min").val()*60);
	}
	if($("#sec").val()){
		time += parseInt($("#sec").val() + 's');
		timeSec = timeSec + parseInt($("#sec").val());
		thisTime += timeSec + parseInt($("#sec").val());
	}

	addRow(ii, $("#titleDescription").val(),$("#exerciceDescription").val(), time, thisTime);
	thisTime = parseInt(0);
	ii++;
	$("#titleDescription").val('');
	$("#exerciceDescription").val('');
	$("#hour").val('');
	$("#min").val('');
	$("#sec").val('');
});

function addRow(ii,title,description,duration, durationSec){
	var elt = '<tr><td>' + ii + '</td><td>' + title +'</td><td class="hidden-xs"><p>' + description+'</p></td><td>' + duration + '</td><td> <button type="submit" class="btn btn-danger btn-sm"> <span class="glyphicon glyphicon-remove"></span> </button></td></tr>';
	$(".tableAppend").append(elt).fadeIn();		
	var hidden = '<input type="hidden" name= exos['+ ii +'][title] value="' + title +'"><br><input type="hidden" name= exos['+ ii +'][description] value="' + description +'"><br><input type="hidden" name= exos['+ ii +'][duration] value="' + duration +'"><br><input type="hidden" name= exos['+ ii +'][durationSec] value="' + durationSec +'">';
	if(ii == 1){
		hidden += '<br><input type="hidden" name="number"  value="' + ii +'" id="number"><br>';
	} else {
		$("#number").val(ii);
	}

	$("#storeExos").append(hidden);


	display(durationSec);	
}

function display(totalSec){

	var hours = parseInt( totalSec / 3600 ) % 24;
	var minutes = parseInt( totalSec / 60 ) % 60;
	var seconds = totalSec % 60;

	var result = (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds  < 10 ? "0" + seconds : seconds);
	$("#totalTimeValue").text(result);	
}

$.fn.serializeObject = function()
{
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};