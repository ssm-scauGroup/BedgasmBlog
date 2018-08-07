var myhead=sessionStorage.getItem("profile");
var username=sessionStorage.getItem("username");
var id=sessionStorage.getItem("id");

window.onload=function(){
	
	loadInfo();
     $('.username2').html(username);
	// showProfile();
}
var profile;
function loadInfo(){
   var settings = {
        url: "http://www.bedgasmblog.cn/user/userinfobyid",
          crossDomain:"true",
        xhrFields:{
            withCredentials:"true"
        },
        type: "POST",
        async:false,
        data: {
            'id':id
        }, 
        dataType: "json",
        success: function(res) {
          
            var detail = "";
            var nickname,signature,email;
            
            nickname = res['user']['nickname'];
            profile = res['user']['profile'];
            signature = res['user']['signature'];
            email=res['user']['email'];	
            // $('#username').html(username);
            $('#finalImg').attr('src',profile);
           
            $('#nickname').val(nickname);
            $('#email').val(email);
            
  			$('#signature').val(signature);
            // var u= "data:image/png;base64,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";
            $(".myhead").css("background",`url(${profile}) no-repeat`);
		    $(".myhead").css("background-position",`center center`);
		    $(".myhead").css("background-size",`100%`);
            sessionStorage.setItem("profile", profile)
            console.log("in success");
            // if(res.total==0){
            //  $(".js-load-more").hide();
            //  articleDiv.innerHTML = "抱歉，搜索不到您要的内容";
            // }
        },

        error: function(res) {
            console.log("load error");
        }
    };
    $.ajax(settings);
}


function showProfile(){
	$(".myhead").css("background",`url(${profile}) no-repeat`);
    $(".myhead").css("background-position",`center center`);
    $(".myhead").css("background-size",`100%`);
}

function saveInfoAjax(){
	var settings = {
        url: "http://www.bedgasmblog.cn/user/modify",
        type: "POST",
        crossDomain:"true",
        xhrFields:{
        withCredentials:"true"
    	},
        data: {
        	'username': username,
        	'nickname': $('#nickname').val(),
        	'email':  $('#email').val(),
        	'signature': $('#signature').val(),
        	'id': id,
            'profile': $("#finalImg").attr("src")
        },
        dataType:"json",
        success: function (res) {
            console.log(res);
            swal('修改成功！','','success');
            setTimeout("location.reload()",2000);
        },
        error: function (res){
        	console.log("save error");
        }
    };
    $.ajax(settings);
}

function checkStatus(){
	var settings = {
        url: "http://www.bedgasmblog.cn/user/userinfo",
        type: "POST",
        data: {
        },
        dataType:"json",
        success: function (res) {
            console.log(res);
            // console.log("save success");
            
        },
        error: function (res){
        	console.log("nologin error");
        }
    };
    $.ajax(settings);
}

function changePwd(){

    var oldpwd=$("#oldpwd").val();
    var pwd=$("#pwd").val();
    var settings = {
        url: "http://www.bedgasmblog.cn/user/modify",
        type: "POST",
        crossDomain:"true",
        xhrFields:{
        withCredentials:"true"
        },
        data: {
            'username': username,
            'oldpassword': oldpwd,
            'password':pwd,
            'id': id
        },
        dataType:"json",
        success: function (res) {
            if(res.success==true){
                swal('密码修改成功！即将退出登录！','','success');
                setTimeout('pHlogout()',2000);
            }
            else if (res.success==false){
                swal('密码修改失败！原密码错误，请重试！','','error');
            }
        },
        error: function (res){
            console.log(oldpwd+"--"+pwd);
            console.log(res);
            console.log("changepwd error");
        }
    };
    $.ajax(settings);
}

function pHlogout() {
    var settings = {
        url: "http://www.bedgasmblog.cn/user/logout",
        type: "POST",
        data: {},
        dataType:"json",
        success: function (data) {
            if (data.success == true) {
                console.log("退出！");
                sessionStorage.clear();
                window.location.href = 'index.html';
            }
        },
    };
    $.ajax(settings);
}