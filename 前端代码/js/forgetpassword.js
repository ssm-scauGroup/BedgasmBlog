function sendemail() {
	swal('请稍后，邮件正在发送中...','','success');
	$("#sendemail").attr("disabled", "disabled");
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
				swal("邮箱格式错误或邮箱还未被注册！","","error");
				$("#sendemail").removeAttr('disabled');
			}
			else {
				swal("已发送邮件！请到您的邮箱接收！","","success");
				$("#sendemail").removeAttr('disabled');
			}
	}
	}

	$.ajax(settings);
	}

	else{
		swal("请输入邮箱！","","warning");
	}

}
