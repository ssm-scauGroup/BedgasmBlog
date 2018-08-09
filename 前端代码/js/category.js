$(function() {
	(function loadcategorylist() {
		var settings = {
			crossDomain: true,
			xhrFields: {
				withCredentials: true
			},
			url: "http://www.bedgasmblog.cn/category/search",
			type: "POST",
			data: {},
			dataType: "json",
			success: function(data) {
				for (var i = 0; i < data.total; i++) {
					var $li = $("<tr>\
                        <td style=\"display: none;\">" + data.categories[i].id + "</td>\
                        <td><input type=\"checkbox\" class=\"input-control\" name=\"checkbox[]\" value=\"\" /></td>\
                        <td class=\"article-title\">" + "<input style=\"border:none;background-color:transparent\" type=\"text\" disabled=\"true\"  value=\"" + data.categories[i].typename + "\"/></td>\
                        <td name=\"total\">" + data.categories[i].typecount + "</td>\
                        <td><a title=\"" + data.categories[i].typename + "\" onclick=\"itemmodify(this)\">修改</a> <a onclick=\"itemdelete(this)\" rel=\"6\">删除</a></td>\
                        </tr>");
					$("#categorylist").append($li);
				}
			}
		};
		$.ajax(settings);
	})();
});

function itemmodify(modifyitem) {
	if ($(modifyitem.parentNode.parentNode).children().eq(0).text() == 1) {
		swal("默认类，无法修改", '', 'error');
		return false;
	}
	var $item = $(modifyitem.parentNode.parentNode).children().eq(2).children().eq(0);
	if ($(modifyitem).text() == "修改") {
		$item.removeAttr("disabled");
		$item.css("border", "inset");
		$(modifyitem).text("保存");
	} else if ($(modifyitem).text() == "保存") {
		if ($(modifyitem).attr("title") == $item.val()) {
			return true;
		}

		var settings = {
			crossDomain: true,
			xhrFields: {
				withCredentials: true
			},
			url: "http://www.bedgasmblog.cn/category/save",
			type: "POST",
			data: {
				"id": $(modifyitem.parentNode.parentNode).children().eq(0).text(),
				"typename": $item.val()
			},
			dataType: "json",
			success: function(data) {
				$(modifyitem).text("修改");
				$item.attr("disabled", "disabled");
				$item.css("border", "none");
			}
		};
		$.ajax(settings);

	}

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
				var categorieid = $(clickeditem.parentNode.parentNode).children().eq(0).text();
				console.log(categorieid);
				var settings = {
					url: "http://www.bedgasmblog.cn/category/delete",
					async: false,
					type: "POST",
					data: {
						"id": categorieid
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
								'栏目已经被删除。',
								'success'
							);
							setTimeout("location.reload()", 1000);
						}

					}
				};
				$.ajax(settings);
			}
		} else {

		}

	})

}

function addcategory() {
	var typename = $("#category-name").val();
	console.log(typename);
	var settings = {
		crossDomain: true,
		xhrFields: {
			withCredentials: true
		},
		url: "http://www.bedgasmblog.cn/category/save",
		type: "POST",
		data: {
			typename: typename
		},
		dataType: "json",
		success: function(data) {
			swal('增加栏目成功！','','success');
			setTimeout('location.reload()',1000);
		}
	};
	$.ajax(settings);
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
					var categorieid = $(this.parentNode.parentNode).children().eq(0).text();
					var settings = {
						url: "http://www.bedgasmblog.cn/category/delete",
						async: false,
						type: "POST",
						data: {
							"id": categorieid
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
									'栏目已经被删除。',
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



function logout() {
	var settings = {
		url: "http://www.bedgasmblog.cn/user/logout",
		type: "POST",
		data: {},
		dataType: "json",
		success: function(data) {
			if (data.success == true) {
				console.log("退出！");
				sessionStorage.clear();
				window.location.href = 'index.html';
			}
		},
	};
	$.ajax(settings);
}