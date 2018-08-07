var userid=sessionStorage.getItem("id");
window.onload=function(){
	loadImage();
}

function getMediaAjax(){
		var settings = {
		url: "http://www.bedgasmblog.cn/media/list",
		  crossDomain:"true",
		xhrFields:{
			withCredentials:"true"
		},
		async: false,
		type: "POST",
		data: {
			'userid':userid
		}, 
		dataType: "json",
		success: function(res) {
			var galleryDiv = document.getElementById("galleryDiv");
			var detail = "";
			var imagepath, releaseDate;
			for (var key in res['medias']) {
				imagepath = res['medias'][key]['imagepath'];
				imgurl="http://www.bedgasmblog.cn/bedgasmBlogContents/" + imagepath;
				releaseDate=res['medias'][key]['releaseDate'];
				detail = `<a href="${imgurl}" title="${releaseDate}"><img src="${imgurl}"></a>`;
				galleryDiv.innerHTML += detail;
					
			}
			//galleryDiv.innerHTML += detail;
			console.log("success");
			console.log(res);
		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);
}

function selectFile(){
	$("#selectImg").trigger("click");
}

 var imgurl;
 function loadImage() {

   var form = document.forms.namedItem("fileinfo");
   form.addEventListener('submit', function(ev) {

     var oOutput = document.querySelector("div"),
       oData = new FormData(form);
     var id = sessionStorage.getItem("id");
     oData.append("userid", id);

     $.ajax({
       url: "http://www.bedgasmblog.cn/media/upload",
       type: "POST",
       data: oData,
       "crossDomain": true,
       "xhrFields": {
         "withCredentials": true
       },
       processData: false, // 不处理数据
       contentType: false, // 不设置内容类型
       error: function() {
         console.log("upload error");
       },
       success: function(data) {
         console.log(data);

         var parentPath = data.parentPath;
         var filePath = data.filePath;
         console.log(parentPath);
         imgurl = "http://www.bedgasmblog.cn/" + parentPath + "/" + filePath;
         var outimgurl = "![](http://www.bedgasmblog.cn/" + parentPath + "/" + filePath + ")";
         console.log(imgurl);
         swal('图片上传成功','','success');
         setTimeout('location.reload()',1000);
         // testEditor.cm.replaceSelection(outimgurl);

       }
     });

     ev.preventDefault();
   }, false);

 }
