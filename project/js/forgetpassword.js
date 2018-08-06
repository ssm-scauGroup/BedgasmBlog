function sendemail() {
	var myemail = $("#inputemail").val();
	console.log(myemail);
	if (myemail!=""){
		var settings = {
		url: "http://www.bedgasmblog.cn/user/resetpassword",
		crossDomain:"true",
		xhrFields:{
			withCredentials:"true"
		},
		type: "POST",
		"data": {
			"email": myemail
		},
		dataType: "json",
		success:function(response) {
			if(response['success']==false){
				alert("邮箱格式错误或邮箱还未被注册！");
			}
			else {
				alert("已发送邮件！请到您的邮箱接收！");
			}
	}
	}

	$.ajax(settings);
	}

	else{
		alert("请输入邮箱！")
	}

}
