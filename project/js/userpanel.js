window.onload = function() {
	loadarticleCount();
	loadosMsg();
	loadcommentCount();
	loadstarCount();
};

function loadcommentCount() {
	var settings = {
		url: "http://www.bedgasmblog.cn/comment/gettotalbyuserid",
		crossDomain: "true",
		xhrFields: {
			withCredentials: "true"
		},
		type: "POST",
		data: {
			'userid': sessionStorage.id
		},
		dataType: "json",
		success: function(res) {
			if (res.success == true) {
				$('#commentcount').text(res.total);
				console.log(res.total);
			}
		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);
}

function loadarticleCount() {
	var settings = {
		url: "http://www.bedgasmblog.cn/article/author",
		crossDomain: "true",
		xhrFields: {
			withCredentials: "true"
		},
		type: "POST",
		data: {
			'id': sessionStorage.id
		},
		dataType: "json",
		success: function(res) {
			if (res.success == true) {
				$('#articlecount').text(res.total);
				console.log(res.total);
			}
		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);
}

function loadstarCount() {
	var settings = {
		crossDomain: true,
		xhrFields: {
			withCredentials: true
		},
		url: "http://www.bedgasmblog.cn/star/list",
		type: "POST",
		data: {
			"subscriber": sessionStorage.id
		},
		dataType: "json",
		success: function(res) {
			if (res.success == true) {
				$('#starcount').text(res.total);
				console.log(res.total);
			}
		}
};
$.ajax(settings);
}


function loadosMsg(){

	var myDate = new Date();//获取系统当前时间
	$("#nowdate").text(myDate.toLocaleString());

	// var userip=$("#userip");
	var settings = {
		crossDomain: true,
		xhrFields: {
			withCredentials: true
		},
		url: "http://www.bedgasmblog.cn/user/getusersysteminfo",
		type: "POST",
		data: {
			
		},
		dataType: "json",
		success: function(res) {
			if (res.success == true) {
				$("#userip").text(res.IP);
				console.log(res.IP);
				$("#browser").text(res.Browser);
				$("#myos").text(res.OS);
				$()
			}
		}
};
$.ajax(settings);
}