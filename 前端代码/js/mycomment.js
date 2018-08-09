window.onload = function() {
	if (sessionStorage.role == 1) {
		console.log("role" + sessionStorage.role);
		loadUsercommentAjax();
	} else {
		console.log("role" + sessionStorage.role);
		loadAllcommentAjax();
	}
};

function loadUsercommentAjax() {
	// body...
	var settings = {
		url: "http://www.bedgasmblog.cn/comment/list",
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
			var commentDiv = document.getElementById("commentlist");
			var detail = "";
			var url = "";
			var commentDate, username, content, articleUrl, commentid;
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
	      				<td><a onclick="itemdelete(this)">&nbsp&nbsp&nbsp删除</a></td>
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
		data: {},
		dataType: "json",
		success: function(res) {
			var commentDiv = document.getElementById("commentlist");
			var detail = "";
			var url = "";
			var commentDate, username, content, articleUrl, commentid;
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

	swal({
		title: '确定删除吗？',
		text: '你将无法恢复它！',
		type: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '确定删除！',
		cancelButtonText: '取消！',
	}).then(function(isConfirm) {
		if (isConfirm.hasOwnProperty("value")) {
			if (isConfirm['value'] == true) {
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

					success: function(data) {
						if (data.success == true) {
							swal(
								'删除！',
								'评论已经被删除。',
								'success'
							);
							setTimeout("location.reload()", 1000);
						}
						else{
							console.log('fail dele');
						}
						console.log(data);
					}
				};
				$.ajax(settings);
			}
		} else {

		}

	})

}


function selecteddelete() {
	var count = $("input[name*=checkbox]:checked").length;
	var msg = "确认删除所选中的" + count + "项？（此操作不可逆）";
	swal({
		title: msg,
		text: '你将无法恢复它！',
		type: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '确定删除！',
		cancelButtonText: '取消！',
	}).then(function(isConfirm) {
		if (isConfirm.hasOwnProperty("value")) {
			if (isConfirm['value'] == true) {
				$("input[name*=checkbox]:checked").each(function() {
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

						success: function(data) {

							if (data.success == true) {
								swal(
									'删除！',
									'评论已经被删除。',
									'success'
								);
								setTimeout("location.reload()", 1000);
							}
						}
					};
					$.ajax(settings);
				});

			}
		} else {

		}
	})

}