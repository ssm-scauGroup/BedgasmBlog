;$(function()
{
	'use strict';

    var backbt = $('.back-to-top');
 
     backbt.css("display","none");
    
    backbt.on('click',function(){
    	$('body').animate({
    		scrollTop:0
    	},800)
    })

    
    $(window).on('scroll',function(){
    	if($(window).scrollTop()>$(window).height()){
    		backbt.fadeIn();

    	}
        else 
        	backbt.fadeOut();
    	
    })
    // $(window).trigger('scroll');


    }
)	