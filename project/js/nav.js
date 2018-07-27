$(document).ready(function () {
    $('.navbar').find('a').each(function () {
            if (this.href == document.location.href || document.location.href.search(this.href) >= 0) {
                $(this).parent().addClass('active'); // this.className = 'active';
            }
        });

});

//  var timer=null;
// function showPromptBox(){
// // var oDiv1=document.getElementById('accountname');
//         var oDiv2=document.getElementById('prompt-box');
       
//             clearTimeout(timer);
//             oDiv2.style.display='block';
//         }

// function closePromptBox(){
//     // var timer=null;
//     var oDiv2=document.getElementById('prompt-box');
//     timer=setTimeout(function(){
//             oDiv2.style.display='none';
//             },500)
// }