window.onload=function(){
	getCateSelection();
	var myDate = new Date();//获取系统当前时间

	console.log(myDate.toLocaleString());
	$("#nowtime").val(myDate.toLocaleString());
};

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
            "author": author
        },
        // dataType:"json",
       	 success: function (res) {
              console.log(res);
              console.log("write success");
                   location.href="articleManager.html";
                 },
           error: function (res) {
                    console.log("write error");
                }
    };
    $.ajax(settings);
                }
	 
}