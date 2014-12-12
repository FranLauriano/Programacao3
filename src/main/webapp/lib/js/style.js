$(document).ready(function() {
	$(function() {
		
		$('ul li.open').removeClass('open');
		var path = window.location.pathname;
		
		if(path.indexOf("/siab/acs/") == 0){
			$('#activeSelect1').addClass('open');
			$('#activeSelect1 > ul').css("display","block");
		}
		if(path.indexOf("/siab/familia/") == 0){
			$('#activeSelect2').addClass('open');
			$('#activeSelect2 > ul').css("display","block");
		}
		if(path.indexOf("/siab/pessoa/") == 0){
			$('#activeSelect3').addClass('open');
			$('#activeSelect3 > ul').css("display","block");
		}
		if(path.indexOf("/siab/endereco/") == 0){
			$('#activeSelect4').addClass('open');
			$('#activeSelect4 > ul').css("display","block");
		}
		if(path.indexOf("/siab/doenca/") == 0){
			$('#activeSelect5').addClass('open');
			$('#activeSelect5 > ul').css("display","block");
		}
		if(path.indexOf("/siab/escolaridade/") == 0){
			$('#activeSelect6').addClass('open');
			$('#activeSelect6 > ul').css("display","block");
		}
		if(path.indexOf("/siab/raca/") == 0){
			$('#activeSelect7').addClass('open');
			$('#activeSelect7 > ul').css("display","block");
		}
		if(path.indexOf("/siab/vinculo/") == 0){
			$('#activeSelect8').addClass('open');
			$('#activeSelect8 > ul').css("display","block");
		}

	});
}); 