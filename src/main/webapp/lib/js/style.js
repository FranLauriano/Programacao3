/**
 * 
 */
$(document).ready(function() {
	$(function() {
		
		$('ul li.active').removeClass('active');
		var path = window.location.pathname;
		
		if(path.indexOf("/siab/acs/") == 0){
			$('#activeSelect1').addClass('active');
		}
		if(path.indexOf("/siab/familia/") == 0){
			$('#activeSelect2').addClass('active');
		}
		if(path.indexOf("/siab/pessoa/") == 0){
			$('#activeSelect3').addClass('active');
		}
		if(path.indexOf("/siab/endereco/") == 0){
			$('#activeSelect4').addClass('active');
		}
		if(path.indexOf("/siab/doenca/") == 0){
			$('#activeSelect5').addClass('active');
		}
		if(path.indexOf("/siab/escolaridade/") == 0){
			$('#activeSelect6').addClass('active');
		}
		if(path.indexOf("/siab/raca/") == 0){
			$('#activeSelect7').addClass('active');
		}
		if(path.indexOf("/siab/vinculo/") == 0){
			$('#activeSelect8').addClass('active');
		}

	});
}); 

//$(document).ready(function() {
//	$('ul li a').click(function() {
//		$('ul li.active').removeClass('active');
//		$(this).closest('li').addClass('active');
//		console.log($(this).attr("href"));
//	});
//});
