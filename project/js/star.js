
// window.onload = function(){

//   var btn = document.getElementById('backtopbtn');
//   var timer = null;
//   var isTop = true;

//   //获取页面可视区高度
//   var clientHeight = document.documentElement.clientHeight;
//  console.log(clientHeight)
   
//   //滚动条滚动时触发
//   window.onscroll = function() {
//   //显示回到顶部按钮
//     var osTop = document.documentElement.scrollTop || document.body.scrollTop;
//     if (osTop >= clientHeight) {
//       btn.style.display = "block";
//     } else {
//       btn.style.display = "block";
//     };
//   //回到顶部过程中用户滚动滚动条，停止定时器
//     if (!isTop) {
//       clearInterval(timer);
//     };
//     isTop = false;
 
//   };
 
//   btn.onclick = function() {
//     //设置定时器
//     timer = setInterval(function(){
//       //获取滚动条距离顶部高度
//       var osTop = document.documentElement.scrollTop || document.body.scrollTop;
//       console.log('osTop '+osTop);
//       var ispeed = Math.floor(-osTop / 7);
//        console.log('ispeed '+ispeed);
//       document.documentElement.scrollTop = document.body.scrollTop = osTop+ispeed;
//       //到达顶部，清除定时器
//       if (osTop == 0) {
//         clearInterval(timer);
//       };
//       isTop = true;
       
//     },30);
//   };
// };

// var flag=0;
function follow(){
  
    var star=$("#star");
     if(star.hasClass("btn-warning")){
      console.log("follow");
        star.html("<span class='glyphicon glyphicon-star-empty' id='plus'></span>&nbsp已订阅");
      // plus.removeClass("glyphicon-plus");
      // plus.addClass("glyphicon-star");
      star.removeClass("btn-warning");
      star.addClass("btn-default");
      

  }
  else{
    var con=window.confirm("确定取消订阅该博主？");
    if(con==true){
      flag=0;
      star.html("<span class='glyphicon glyphicon-plus' id='plus'></span>&nbsp订阅");
      star.removeClass("btn-default");
      star.addClass("btn-warning");
    }
    
      
  }
  
 } 




//  var timer=null;
// function showPromptBox(){
// // var oDiv1=document.getElementById('accountname');
//         var oDiv2=document.getElementById('prompt-box');
       

//         // oDiv1.onmouseover=oDiv2.onmouseover=function(){
//             clearTimeout(timer);
//             oDiv2.style.display='block';
        
//         // oDiv1.onmouseout=oDiv2.onmouseout=function(){
//         //     timer=setTimeout(function(){
//         //     oDiv2.style.display='none';
//         //     },500)
//         // }
//     }

// function closePromptBox(){
//     // var timer=null;
//     var oDiv2=document.getElementById('prompt-box');
//     timer=setTimeout(function(){
//             oDiv2.style.display='none';
//             },500)
// }