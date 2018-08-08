 window.onload = function() {
 	$("#addusername").blur(function() {
 		reg = /^[a-zA-Z0-9_]{4,16}$/;
 		if ($(this).val() == "") {
 			swal('用户名为空！');
 		} else if (!reg.test($("#addusername").val())) {
 			swal('用户名格式错误，只能输入数字、字母、下划线的组合！');
 		} else {
 			checkusername();
 		}
 	});
 	$("#addpassword").blur(function() {
 		reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[\s\S]{8,30}$/;
 		if ($(this).val() == "") {
 			swal('密码为空！');
 		} else if (!reg.test($("#addpassword").val())) {
 			swal('密码格式错误，至少包含数字、大小写字母！');
 		} else {
 			passok = true;
 		}
 	});
 	$("#addemail").blur(function() {
 		reg = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
 		if ($(this).val() == "") {
 			swal('邮箱为空！');
 		} else if (!reg.test($("#addemail").val())) {
 			swal('请输入正确的邮箱格式！');
 		} else {
 			checkemail();
 		}
 	});
 	$("#addrole").blur(function() {
 		if ($(this).val() == "") {
 			swal('请输入用户角色！');
 		} else if ($(this).val() == "0" || $(this).val() == "1") {
 			roleok = true;
 		} else {
 			swal('请输入正确的角色(0/1)！');
 		}
 	});

 }

 var nameok = false,
 	passok = false,
 	emailok = false,
 	roleok = false;

 function checkusername() {
 	var settings = {
 		url: "http://www.bedgasmblog.cn/user/isValid",
 		crossDomain: "true",
 		xhrFields: {
 			withCredentials: "true"
 		},
 		async: false,
 		type: "POST",
 		data: {
 			username: $("#addusername").val(),
 		},
 		dataType: "json",
 		success: function(data) {
 			if (data.isValid == false) {
 				swal('用户名已被注册！');
 			} else if (data.isValid == true) {
 				nameok = true;
 			}
 		},
 	};
 	$.ajax(settings);
 }

 function checkemail() {
 	var settings = {
 		url: "http://www.bedgasmblog.cn/user/isValid",
 		crossDomain: "true",
 		xhrFields: {
 			withCredentials: "true"
 		},
 		type: "POST",
 		data: {
 			email: $("#addemail").val(),
 		},
 		dataType: "json",
 		success: function(data) {
 			if (data.isValid == false) {
 				swal('邮箱已被注册！');
 			} else if (data.isValid == true) {
 				emailok = true;
 			}
 		},
 	};
 	$.ajax(settings);
 }

 function adduser() {
 	var username, email, password, role;
 	username = $("#addusername").val();
 	email = $("#addemail").val();
 	password = $("#addpassword").val();
 	role = $("#addrole").val();
 	if (nameok == true && passok == true && emailok == true && roleok == true) {
 		var settings = {
 			url: "http://www.bedgasmblog.cn/user/add",
 			crossDomain: "true",
 			xhrFields: {
 				withCredentials: "true"
 			},
 			type: "POST",
 			data: {
 				'username': username,
 				'password': password,
 				'email': email,
 				'role': role
 			},
 			dataType: "json",
 			success: function(res) {
 				swal('添加用户成功');
 			},

 			error: function(res) {
 				console.log("error");
 			}
 		};
 		$.ajax(settings);
 	}
 	else{
 		swal('信息填写有误请检查！');
 	}

 }