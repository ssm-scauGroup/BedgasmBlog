window.onload = function() {
	var more = $('.fa-comment-o');
	more.click(function() {
		$('html,body').animate({
			scrollTop: ($('.comment-title').offset().top) - 50
		}, 800);
	});


	var url = window.location.href;
	if (url.indexOf("?") > 0) {
		getCateArticle1();
	} else {
		getHomeAjax();

	}

	// getHomeAjax();
	loadCateAjax();
	backtotop();
	loadHotAjax();
	loadlinkAjax();


};


function backtotop() {
	var btn = document.getElementById('backtopbtn');
	// console.log(btn);
	var timer = null;
	var isTop = true;

	//获取页面可视区高度
	var clientHeight = document.documentElement.clientHeight;
	// console.log(clientHeight)

	//滚动条滚动时触发
	window.onscroll = function() {
		//显示回到顶部按钮
		var osTop = document.documentElement.scrollTop || document.body.scrollTop;
		if (osTop >= clientHeight) {
			btn.style.display = "block";
		} else {
			// console.log(btn.innerHTML);
			btn.style.display = "none";
		};
		//回到顶部过程中用户滚动滚动条，停止定时器
		if (!isTop) {
			clearInterval(timer);
		};
		isTop = false;

	};

	btn.onclick = function() {
		//设置定时器
		timer = setInterval(function() {
			//获取滚动条距离顶部高度
			var osTop = document.documentElement.scrollTop || document.body.scrollTop;
			// console.log('osTop ' + osTop);
			var ispeed = Math.floor(-osTop / 7);
			// console.log('ispeed ' + ispeed);
			document.documentElement.scrollTop = document.body.scrollTop = osTop + ispeed;
			//到达顶部，清除定时器
			if (osTop == 0) {
				clearInterval(timer);
			};
			isTop = true;

		}, 30);
	};
}



function getHomeAjax() {


	var settings = {
		url: "http://www.bedgasmblog.cn/article/list",
		  crossDomain:"true",
		xhrFields:{
			withCredentials:"true"
		},
		type: "POST",
		data: {
			'page': "1",
			'rows': "4"
		}, 
		dataType: "json",
		success: function(res) {
			var articleDiv = document.getElementById("articleDiv");
			var detail = "";
			var title, summary, url, releaseDate, nickname, commentCount,authorUrl;
			for (var key in res['posts']) {
				title = res['posts'][key]['title'];
				summary = res['posts'][key]['summary'];
				url = "./article.html?id=" + res['posts'][key]['id'];
				authorUrl="./personHome.html?id=" + res['posts'][key]['author'];
				releaseDate = res['posts'][key]['releaseDate'];
				commentCount = res['posts'][key]['replyCount'];
				nickname=res['posts'][key]['user']['nickname'];
				detail +=
					`<article class="blog-post">
						<div class="blog-post-body">
							<h2><a href="${url}">${title}</a></h2>
							<div class="post-meta"><span>by <a href="${authorUrl}">${nickname}</a></span>/<span><i class="fa fa-clock-o"></i>${releaseDate}</span>/<span><i class="fa fa-comment-o"></i>${commentCount}</span></div>
							<p>${summary}</p>
							<div class="read-more"><a href="${url}">Continue Reading</a></div>
						</div>
					</article>`;
			}
			articleDiv.innerHTML += detail;
			console.log("HOME success");
		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);
}

function searchClickAjax(){
	var searchkey=$("#searchkey").val();
	$(".js-load-more").hide();
	console.log(searchkey);
	
		var settings = {
		url: "http://www.bedgasmblog.cn/article/search",
		  crossDomain:"true",
		xhrFields:{
			withCredentials:"true"
		},
		type: "POST",
		data: {
			'keyword': searchkey
		}, 
		dataType: "json",
		success: function(res) {
			var articleDiv = document.getElementById("articleDiv");
			var detail = "";
			var title, summary, url, releaseDate, username, replyCount,authorUrl;
			for (var key in res['articles']) {
				title = res['articles'][key]['title'];
				summary = res['articles'][key]['summary'];
				url = "./article.html?id=" + res['articles'][key]['id'];
				authorUrl="./personHome.html?id=" + res['articles'][key]['author'];
				releaseDate = res['articles'][key]['releaseDate'];
				replyCount = res['articles'][key]['replyCount'];
				username = res['articles'][key]['user']['nickname'];
				detail +=
					`<article class="blog-post">
						<div class="blog-post-body">
							<h2><a href="${url}">${title}</a></h2>
							<div class="post-meta"><span>by <a href="${authorUrl}">${username}</a></span>/<span><i class="fa fa-clock-o"></i>${releaseDate}</span>/<span><i class="fa fa-comment-o"></i> <a href="#">${replyCount}</a></span></div>
							<p>${summary}</p>
							<div class="read-more"><a href="${url}">Continue Reading</a></div>
						</div>
					</article>`;
			}
			console.log(res);
		
			articleDiv.innerHTML = detail;
			console.log("SEARCH success");
			if(res.total==0){
				$(".js-load-more").hide();
				articleDiv.innerHTML = "抱歉，搜索不到您要的内容";
			}
		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);

}


var page=2;

function loadmoreAjax() {


	var settings = {
		url: "http://www.bedgasmblog.cn/article/list",
		  crossDomain:"true",
		xhrFields:{
			withCredentials:"true"
		},
		type: "POST",
		aysnc:false,
		data: {
			'page': page,
			'rows': '4'
		}, 
		dataType: "json",
		success: function(res) {
			var articleDiv = document.getElementById("articleDiv");
			var detail = "";
			var title, summary, url, releaseDate, nickname, commentCount,authorUrl;
			for (var key in res['posts']) {
				title = res['posts'][key]['title'];
				summary = res['posts'][key]['summary'];
				url = "./article.html?id=" + res['posts'][key]['id'];
				authorUrl="./personHome.html?id=" + res['posts'][key]['author'];
				releaseDate = res['posts'][key]['releaseDate'];
				commentCount = res['posts'][key]['replyCount'];
				nickname=res['posts'][key]['user']['nickname'];
				detail +=
					`<article class="blog-post">
						<div class="blog-post-body">
							<h2><a href="${url}">${title}</a></h2>
							<div class="post-meta"><span>by <a href="${authorUrl}">${nickname}</a></span>/<span><i class="fa fa-clock-o"></i>${releaseDate}</span>/<span><i class="fa fa-comment-o"></i>${commentCount}</span></div>
							<p>${summary}</p>
							<div class="read-more"><a href="${url}">Continue Reading</a></div>
						</div>
					</article>`;
			}
			articleDiv.innerHTML += detail;
			console.log("success");
			page=page+1;
			console.log(page);
			if(res['total']<4)
			{
				$(".js-load-more").hide();
			}


		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);

}

function loadCateAjax(){
	// console.log(searchkey	
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
			var categorylist = document.getElementById("categorylist");
			var detail = "";
			var url;
			var typename;
			for (var key in res['categories']) {
				typename = res['categories'][key]['typename'];
				url =res['categories'][key]['id'];
				detail +=
					`<li><a onclick="getCateArticle(${url})" href="index.html#maodian" >${typename}</a></li>`
			}
		
			categorylist.innerHTML = detail;
			console.log("success");
			// if(res.total==0){
			// 	$(".js-load-more").hide();
			// 	articleDiv.innerHTML = "抱歉，搜索不到您要的内容";
			// }
		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);

}

function getCateArticle(typeid) {
	// var regex = /.*?id=([0-9]*)/ig;
	//         var detailUrl = window.location.href;
	//         var detailId = regex.exec(detailUrl)[1]
	$(".js-load-more").hide();
	if (typeid) {
		console.log(typeid);
		var html = $.ajax({
			async: true,
			  crossDomain:"true",
		xhrFields:{
			withCredentials:"true"
		},
			url: "http://www.bedgasmblog.cn/article/category",
			type: "post",
			data: {
				'id': typeid
			},
			dataType: "json",
			success: function(res) {
				var articleDiv = document.getElementById("articleDiv");
				var detail = "";
				var title, summary, url, releaseDate, username, replyCount,authorUrl;
				for (var key in res['articles']) {
					title = res['articles'][key]['title'];
					summary = res['articles'][key]['summary'];
					url = "./article.html?id=" + res['articles'][key]['id'];
					authorUrl="./personHome.html?id=" + res['articles'][key]['author'];
					username=res['articles'][key]['user']['nickname'];
					releaseDate = res['articles'][key]['releaseDate'];
					replyCount = res['articles'][key]['replyCount'];
					detail +=
						`<article class="blog-post">
								<div class="blog-post-body">
									<h2><a href="${url}">${title}</a></h2>
									<div class="post-meta"><span>by <a href="${authorUrl}">${username}</a></span>/<span><i class="fa fa-clock-o"></i>${releaseDate}</span>/<span><i class="fa fa-comment-o"></i> <a href="#">${replyCount}</a></span></div>
									<p>${summary}</p>
									<div class="read-more"><a href="${url}">Continue Reading</a></div>
								</div>
							</article>`;
				}
				articleDiv.innerHTML = detail;
				console.log("success");
				if (res.total == 0) {
					$(".js-load-more").hide();
					articleDiv.innerHTML = "抱歉，该类别还没有文章！";
				}
			},
			error: function(res) {
				console.log("error");
			}
		})
	} else {
		alert("没有id");
	}
}

function getCateArticle1() {
	//http://localhost:8080/ssmDemo4/static/detailArticle.html?id=7
	$(".js-load-more").hide();
	console.log("cate");
	var regex = /.*?id=([0-9]*)/ig;
	var detailUrl = window.location.href;
	var detailId = regex.exec(detailUrl)[1]
	if (detailId) {
		console.log(detailId);
		var html = $.ajax({
			async: true,
			crossDomain: "true",
			xhrFields: {
				withCredentials: "true"
			},
			url: "http://www.bedgasmblog.cn/article/category",
			type: "post",
			data: {
				'id': detailId
			},
			dataType: "json",
			success: function(res) {
				console.log("id:" + detailId);
				var articleDiv = document.getElementById("articleDiv");
				var detail = "";
				var title, summary, url, releaseDate, username, commentCount, authorUrl;
				for (var key in res['articles']) {
					title = res['articles'][key]['title'];
					summary = res['articles'][key]['summary'];
					url = "./article.html?id=" + res['articles'][key]['id'];
					authorUrl = "./personHome.html?id=" + res['articles'][key]['author'];
					releaseDate = res['articles'][key]['releaseDate'];
					commentCount = res['articles'][key]['replyCount'];
					username = res['articles'][key]['user']['nickname'];
					detail +=
						`<article class="blog-post">
								<div class="blog-post-body">
									<h2><a href="${url}">${title}</a></h2>
									<div class="post-meta"><span>by <a href="${authorUrl}">${username}</a></span>/<span><i class="fa fa-clock-o"></i>${releaseDate}</span>/<span><i class="fa fa-comment-o"></i> ${commentCount}</span></div>
									<p>${summary}</p>
									<div class="read-more"><a href="${url}">继续阅读</a></div>
								</div>
							</article>`;
				}

				articleDiv.innerHTML += detail;
				console.log("success");
			},
			error: function(res) {
				console.log("error");
			}
		})
	} else {
		alert("没有id");
	}
}

function loadHotAjax() {
	var settings = {
		url: "http://www.bedgasmblog.cn/article/hot",
		crossDomain: "true",
		xhrFields: {
			withCredentials: "true"
		},
		type: "POST",
		data: {
			'rows': 5
		},
		dataType: "json",
		success: function(res) {
			var hotarticle = document.getElementById("hotarticle");
			var detail = "";
			var url;
			var replyCount;
			var title;
			var releaseDate;
			for (var key in res['posts']) {
				releaseDate = res['posts'][key]['releaseDate'];
				replyCount = res['posts'][key]['replyCount'];
				title = res['posts'][key]['title'];
				url = "./article.html?id=" + res['posts'][key]['id'];
				detail +=
					`<article class="widget-post">
								<div class="post-body">
									<h2>
										<a href="${url}">${title}</a>
									</h2>
									<div class="post-meta">
										<span>
											<i class="fa fa-clock-o"></i> ${releaseDate}</span>
										<span>
											<i class=" fa fa-comment-o "></i> ${replyCount}</span>
									</div>
								</div>
							</article>`;
			}

			hotarticle.innerHTML = detail;
			console.log("hot success");
			// if(res.total==0){
			// 	$(".js-load-more").hide();
			// 	articleDiv.innerHTML = "抱歉，搜索不到您要的内容";
			// }
		},

		error: function(res) {
			console.log("error");
		}
	};
	$.ajax(settings);
}

function loadlinkAjax() {
	var settings = {
		crossDomain: true,
		xhrFields: {
			withCredentials: true
		},
		url: "http://www.bedgasmblog.cn/links/list",
		type: "POST",
		data: {},
		dataType: "json",
		success: function(res) {
			var detail = "";
			var url, linkname;
			var linkDiv = document.getElementById("linklist");
			for (var key in res['links']) {
				url = res['links'][key]['linkurl'];
				linkname = res['links'][key]['linkname'];
				detail += `<li><a href="${url}">${linkname}</a></li>`;
			}
			linkDiv.innerHTML += detail;
			console.log("linklist success");
		},
		error: function(res) {
			console.log("link error");
		}
	};
	$.ajax(settings);
}