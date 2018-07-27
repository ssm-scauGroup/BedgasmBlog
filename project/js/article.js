window.onload =function(){
	var more = $('.fa-comment-o');
 	more.click(function(){
 	$('html,body').animate({scrollTop:($('.comment-title').offset().top)-50}, 800);
 });
};