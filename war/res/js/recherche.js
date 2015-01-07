function callbackSuccess(data){
	console.log(data);
	var size = Object.keys(data).length;
	var nbLigne = Math.ceil(size/4);
	console.log(nbLigne);
	if(size =! 0){
		
		
		$('<div/>', {
		    id: 'foo',
		    href: 'http://google.com',
		    title: 'Become a Googler',
		    rel: 'external',
		    text: 'Go to Google!'
		}).appendTo('#mySelector');
		
		
		
		
	}
	
	
	
	
	
	
	
}

function callbackError(){}
function loaderFunc(){}