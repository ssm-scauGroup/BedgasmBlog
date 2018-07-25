// ;$(function()
// {
// 	'use strict';

//     var backbt = $('.back-to-top');
 
//      backbt.css("display","none");
    
//     backbt.on('click',function(){
//     	$('body').animate({
//     		scrollTop:0
//     	},800)
//     })

    
//     $(window).on('scroll',function(){
//     	if($(window).scrollTop()>$(window).height()){
//     		backbt.fadeIn();

//     	}
//         else 
//         	backbt.fadeOut();
    	
//     })
//     $(window).trigger('scroll');


//     }
// )	

window.onload = function(){
  var btn = document.getElementById('backtopbtn');
  var timer = null;
  var isTop = true;

  //获取页面可视区高度
  var clientHeight = document.documentElement.clientHeight;
 console.log(clientHeight)
   
  //滚动条滚动时触发
  window.onscroll = function() {
  //显示回到顶部按钮
    var osTop = document.documentElement.scrollTop || document.body.scrollTop;
    if (osTop >= clientHeight) {
      btn.style.display = "block";
    } else {
      btn.style.display = "none";
    };
  //回到顶部过程中用户滚动滚动条，停止定时器
    if (!isTop) {
      clearInterval(timer);
    };
    isTop = false;
 
  };
 
  btn.onclick = function() {
    //设置定时器
    timer = setInterval(function(){
      //获取滚动条距离顶部高度
      var osTop = document.documentElement.scrollTop || document.body.scrollTop;
      console.log('osTop '+osTop);
      var ispeed = Math.floor(-osTop / 7);
       console.log('ispeed '+ispeed);
      document.documentElement.scrollTop = document.body.scrollTop = osTop+ispeed;
      //到达顶部，清除定时器
      if (osTop == 0) {
        clearInterval(timer);
      };
      isTop = true;
       
    },30);
  };
};
