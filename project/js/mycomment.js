window.onload = function() {
	if(sessionStorage.role==1){
        console.log("role"+sessionStorage.role);
        loadUsercommentAjax();
    }
    else{
        console.log("role"+sessionStorage.role);
        loadAllcommentAjax();
    }
};

function loadUsercommentAjax () {
	// body...
	var settings = {
		url: "http://www.bedgasmblog.cn/comment/list",
		crossDomain: "true",
		xhrFields: {
			withCredentials: "true"
		},
		type: "POST",
		data: {
			'userid':sessionStorage.id
		},
		dataType: "json",
		success: function(res) {
			var commentDiv = document.getElementById("commentlist");
			var detail = "";
			var url = "";
			var commentDate, username, content, articleUrl , commentid;
			for (var key in res['comments']) {
				articleUrl = "./article.html?id=" + res['comments'][key]['articleid'];
				commentid = res['comments'][key]['id'];
				username = res['comments'][key]['user']['nickname'];
				commentDate = res['comments'][key]['commentDate'];
				content = res['comments'][key]['content'];
				detail +=
					` <tr>
						<td style="display: none;">${commentid}</td>
	      				<td><input type="checkbox" class="input-control" name="checkbox[]" value="" /></td>
	      				<td class="article-title"><a href="${articleUrl}">${content}</a></td>
	      				<td>${username}</td>
	      				<td>${commentDate}</td>
	      				<td><a href="#">&nbsp&nbsp&nbsp删除</a></td>
	      			  </tr> `;
			}


			commentDiv.innerHTML += detail;
			console.log("commentsuccess");
		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);
}

function loadAllcommentAjax() {
	// body... 
	var settings = {
		url: "http://www.bedgasmblog.cn/comment/list",
		crossDomain: "true",
		xhrFields: {
			withCredentials: "true"
		},
		type: "POST",
		data: {
		},
		dataType: "json",
		success: function(res) {
			var commentDiv = document.getElementById("commentlist");
			var detail = "";
			var url = "";
			var commentDate, username, content, articleUrl , commentid;
			for (var key in res['comments']) {
				commentid = res['comments'][key]['id'];
				articleUrl = "./article.html?id=" + res['comments'][key]['articleid'];
				username = res['comments'][key]['user']['nickname'];
				commentDate = res['comments'][key]['commentDate'];
				content = res['comments'][key]['content'];
				detail +=
					` <tr>
						<td style="display: none;">${commentid}</td>
	      				<td><input type="checkbox" class="input-control" name="checkbox[]" value="" /></td>
	      				<td class="article-title"><a href="${articleUrl}">${content}</a></td>
	      				<td>${username}</td>
	      				<td>${commentDate}</td>
	      				<td><a onclick="itemdelete(this)">&nbsp&nbsp&nbsp删除</a></td>
	      			  </tr> `;
			}
			commentDiv.innerHTML += detail;
			console.log("commentsuccess");
			console.log(commentid);
		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);
}

function itemdelete(clickeditem) {
    var r = confirm("确认删除评论？");
    if (r == true) {
        var linkid = $(clickeditem.parentNode.parentNode).children().eq(0).text();
        console.log(linkid);
        var settings = {
            url: "http://www.bedgasmblog.cn/comment/delete",
            async: false,
            type: "POST",
            data: {
                "id": linkid
            },
            dataType: "json",
            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },

            success: function (data) {
                if (data.success == true) {
                    $(clickeditem.parentNode.parentNode).remove();
                    location.reload();
                }
                console.log(data);
            }
        };
        $.ajax(settings);
    }
}


function selecteddelete() {
    var count = $("input[name*=checkbox]:checked").length;
    r = confirm("确认删除所选中的" + count + "项？（此操作不可逆）");
    var isdelete = true;
    if (r == true) {
        $("input[name*=checkbox]:checked").each(function () {
            var linkid = $(this.parentNode.parentNode).children().eq(0).text();
            var settings = {
                url: "http://www.bedgasmblog.cn/comment/delete",
                async: false,
                type: "POST",
                data: {
                    "id": linkid
                },
                dataType: "json",
                crossDomain: true,
                xhrFields: {
                    withCredentials: true
                },

                success: function (data) {
                    
                    if (data.success == false) {
                        console.log("批量操作出错！");
                        isdelete = false;
                    }
                }
            };
            $.ajax(settings);
        });

        if (isdelete) {
            $("input[name*=checkbox]:checked").each(function () {
                $(this.parentNode.parentNode).remove();
                location.reload();
            });
        }
    }
}
