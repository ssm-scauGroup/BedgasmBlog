window.onload=function(){ 
	loadImage();
	getCateSelection();
 

};

// var articleId;
// var regex = /.*?id=([0-9]*)/ig;
//     var detailUrl = window.location.href;
//     articleId = regex.exec(detailUrl)[1];

var username=sessionStorage.getItem("username");
var author=sessionStorage.getItem("id");

function getCateSelection(){
	var settings = {
		url: "http://www.bedgasmblog.cn/category/search",
		  crossDomain:"true",
        xhrFields:{
            withCredentials:"true"
        },
		type: "POST",
		data: {
	
		}, 
		dataType: "json",
		success: function(res) {
			var categorylist = document.getElementById("category-list");
			var detail = "";
			var url;
			var typename;
			for (var key in res['categories']) {
				typename = res['categories'][key]['typename'];
				url =res['categories'][key]['id'];
				detail +=
					`<option value ="${url}">${typename}</option>`
					
			}
		
			categorylist.innerHTML += detail;
			console.log("catesuccess");
			// if(res.total==0){
			// 	$(".js-load-more").hide();
			// 	articleDiv.innerHTML = "抱歉，搜索不到您要的内容";
			// }
		},

		error: function(res) {
			console.log("cateerror");
		}
	};
	$.ajax(settings);
}


function writeAjax(){
	 
	
	 var title=$("#title").val();
	 console.log(title);
	 var tags=$("#tags").val();
	 console.log(tags);
	 var content=testEditor.getHTML();
   var mdcontent=testEditor.getMarkdown();
	 var summary = testEditor.getMarkdown().substr(0,30); 
	 var blogtypeid=$("#category-list").val();
	 if (title == null || title == '') {
                    alert("系统提示:请输入标题！");
                } else if (blogtypeid == null || blogtypeid == '') {
                    alert("系统提示:请选择博客类型！");
                } else if (content == null || content == '') {
                    alert("系统提示:请编辑博客内容！");
                } else
                {
                	var settings = {
        url: "http://www.bedgasmblog.cn/article/save",
          crossDomain:"true",
        xhrFields:{
            withCredentials:"true"
        },
        type: "POST",
        data: {
            "title": title,
            "summary": summary,
            "content": content,
            "tags": tags,
            "blogtypeid": blogtypeid,
            "author": author,
            "username":username,
             "mdcontent": mdcontent
        },
        // dataType:"json",
       	 success: function (res) {
              console.log(res);
              swal({text:'发布成功！请返回文章管理查看！',timer:3000});
              console.log("write success");
             
                 },
           error: function (res) {
                    console.log("write error");
                }
    };
    $.ajax(settings);
                }
	 
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
         testEditor.cm.replaceSelection(outimgurl);

       }
     });

     ev.preventDefault();
   }, false);

 }


 // testEditor.cm.replaceSelection("fasdfasdf");
 // function loadOldArticle() {

 //   console.log("articleId: " + articleId);
 //   var settings = {
 //     url: "http://www.bedgasmblog.cn/article/detail",
 //     crossDomain: "true",
 //     xhrFields: {
 //       withCredentials: "true"
 //     },
 //     type: "POST",
 //     data: {
 //       'id': articleId
 //     },
 //     dataType: "json",
 //     success: function(res) {
 //       var title, blogtypeid, content, tags;
 //       console.log("loadarticlesuccess");

 //       // if(res.total==0){
 //       //  $(".js-load-more").hide();
 //       //  articleDiv.innerHTML = "抱歉，搜索不到您要的内容";

 //       // }
 //       title = res['post']['title'];
 //       blogtypeid = res['post']['blogtypeid'];
 //       content = res['post']['content'];
 //       tags = res['post']['tags'];
 //       $("#title").val(title);
 //       $("#category-list").val(blogtypeid);
 //       testEditor.cm.replaceSelection(content);
 //       $("#tags").val(tags);

 //     },

 //     error: function(res) {
 //       console.log("loadartilce error");
 //     }

 //   };
 //   $.ajax(settings);

 // }