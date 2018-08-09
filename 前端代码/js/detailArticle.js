 window.onload =function(){
    testfunction();
    loadDetailAjax();
    increaseclick();
    loadHotAjax();
    loadCommentAjax();
    loadHeadAjax();
 }

var isLogin=false;
var articleId;
var regex = /.*?id=([0-9]*)/ig;
    var detailUrl = window.location.href;
    articleId = regex.exec(detailUrl)[1];
var username=sessionStorage.getItem("username");
var myid=sessionStorage.getItem("id");
var myhead=sessionStorage.getItem("profile");

function testfunction() {
        var settings = {
            url: "http://www.bedgasmblog.cn/user/userinfo",
              crossDomain:"true",
              async:false,
        xhrFields:{
            withCredentials:"true"
        },
            type: "POST",
            data: {},
            dataType: "json",
            success: function (data) {
                //用户没登录
                if(data.success==false){
                    console.log(isLogin);
                }
                else{
                    isLogin=true;
                    console.log(isLogin);
                }
                // console.log("userinfo:"+data.success);
                
            }
        };
        $.ajax(settings);
}
function loadHeadAjax(){
    $("#myhead").css("background",`url(${myhead}) no-repeat`);
    $("#myhead").css("background-position",`center center`);
    $("#myhead").css("background-size",`100%`);
}


function loadDetailAjax() {
    //正则表达式匹配id
    //http://localhost:8080/ssmDemo4/static/detailArticle.html?id=7
    var regex = /.*?id=([0-9]*)/ig;
    var detailUrl = window.location.href;
    var detailId = regex.exec(detailUrl)[1];
    if (detailId) {
        articleId=detailId;
        console.log("aid"+articleId);
        console.log(detailId);
        var html = $.ajax({
            async: true,
            crossDomain: "true",
            xhrFields: {
                withCredentials: "true"
            },
            url: "http://www.bedgasmblog.cn/article/detail",
            type: "post",
            data: {
                'id': detailId
            },
            dataType: "json",
            success: function(res) {
                var mainArticle = document.getElementById("mainArticle");
                var detail = "";
                var url = "";
                var title, username, releaseDate, replyCount, content, authorUrl;
                title = res['post']['title'];
                username = res['post']['user']['nickname'];
                releaseDate = res['post']['releaseDate'];
                authorUrl = "./personHome.html?id=" + res['post']['author'];
                replyCount = res['post']['replyCount'];
                content = res['post']['content'];
                detail +=
                    `<article class="blog-post">    
                        <div class="blog-post-body">
                            <h2>${title}</h2>
                            <div class="post-meta"><span>by <a href="${authorUrl}">${username}</a></span>/<span><i class="fa fa-clock-o"></i>${releaseDate}</span>/<span<i class="fa fa-comment-o"></i>${replyCount}</span>
                            </div>
                            <div style="text-align: left" class="wenzhang">${content}</div>
                        </div>
                    </article>`;
                console.log(res);
                mainArticle.innerHTML = detail;
                console.log("success");
            },
            error: function(res) {
                console.log("error");
            }
        })
    } else {
        swal("没有id");
    }
    loadCateAjax();
}

function searchClickAjax() {
    var searchkey = $("#searchkey").val();
    console.log(searchkey);

    var settings = {
        url: "http://www.bedgasmblog.cn/article/search",
        type: "POST",
          crossDomain:"true",
        xhrFields:{
            withCredentials:"true"
        },
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
                detail +=
                    `<article class="blog-post">
                        <div class="blog-post-body">
                            <h2><a href="${url}">${title}</a></h2>
                            <div class="post-meta"><span>by <a href="${authorUrl}">${username}</a></span>/<span><i class="fa fa-clock-o"></i>${releaseDate}</span>/<span><i class="fa fa-comment-o"></i>${replyCount}</span></div>
                            <p>${summary}</p>
                            <div class="read-more"><a href="${url}">Continue Reading</a></div>
                        </div>
                    </article>`;
            }

            articleDiv.innerHTML = detail;
            console.log("success");
            if (res.total == 0) {
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

function loadCateAjax() {
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
                url = "./index.html?id=" + res['categories'][key]['id'];
                detail +=
                    `<li><a href="${url}"#"maodian">${typename}</a></li>`
            }

            categorylist.innerHTML = detail;
            console.log("success");
            // if(res.total==0){
            //  $(".js-load-more").hide();
            //  articleDiv.innerHTML = "抱歉，搜索不到您要的内容";
            // }
        },

        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);

}

function increaseclick(){
    var settings = {
        url: "http://www.bedgasmblog.cn/article/increaseclick",
          crossDomain:"true",
        xhrFields:{
            withCredentials:"true"
        },
        type: "POST",
        data: {
            'id':articleId
        },
        dataType: "json",
        success: function(res) {
            console.log("clickCount already +1");
        },

        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);
}

function loadHotAjax(){
    var settings = {
        url: "http://www.bedgasmblog.cn/article/hot",
          crossDomain:"true",
        xhrFields:{
            withCredentials:"true"
        },
        type: "POST",
        data: {
            'rows':5
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
            //  $(".js-load-more").hide();
            //  articleDiv.innerHTML = "抱歉，搜索不到您要的内容";
            // }
        },

        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);
}

function loadCommentAjax(){
    var regex = /.*?id=([0-9]*)/ig;
    var detailUrl = window.location.href;
    var detailId = regex.exec(detailUrl)[1];
    if (detailId) {
        articleId = detailId;
        console.log("aid" + articleId);
        console.log(detailId);
        var html = $.ajax({
            async: true,
            crossDomain: "true",
            xhrFields: {
                withCredentials: "true"
            },
            url: "http://www.bedgasmblog.cn/comment/list",
            type: "post",
            data: {
                'articleid': detailId
            },
            dataType: "json",
            success: function(res) {
                var commentDiv = document.getElementById("commentDiv");
                var detail = "";
                var url = "";
                var commentDate, username, content,commentUserUrl,profile;
               for (var key in res['comments']){
                    username = res['comments'][key]['user']['nickname'];
                    commentDate = res['comments'][key]['commentDate'];
                    commentUserUrl="./personHome.html?id=" + res['comments'][key]['user']['id'];
                    content = res['comments'][key]['content'];
                    profile=res['comments'][key]['user']['profile'];
                    detail +=
                       ` <div class="comment-list-box clearfix">
                            <div class="comment-headimage" style="background:url(${profile}) no-repeat;background-position:center center;background-size:100%"></div>
                            <div class="comment-content">
                                <h2><a href="${commentUserUrl}" >${username}</a><span> &nbsp &nbsp<i class="fa fa-clock-o"></i>${commentDate}</span></h2>
                                <p>${content}</p>
                            </div>
                        </div> `;
               }
                
            
                commentDiv.innerHTML += detail;
                console.log("commentsuccess");
            },
            error: function(res) {
                console.log("commenterror");
            }
        })
    } else {
        swal("没有id");
    }
}

function addComment() {
    if (isLogin) {
        var mycomment = $("#mycomment").val();
        if (mycomment.length < 140) {
            var settings = {
                url: "http://www.bedgasmblog.cn/comment/save",
                crossDomain: "true",
                xhrFields: {
                    withCredentials: "true"
                },
                type: "POST",
                data: {
                    'content': mycomment,
                    'userid': myid,
                    'articleid': articleId
                },
                dataType: "json",
                success: function(res) {
                    swal('评论成功！','','success');
                    setTimeout('location.reload()',1000);
                },

                error: function(res) {
                    console.log("add error");
                }
            };
            $.ajax(settings);
        } else
            swal('评论过长，140字以内！','','success');
    } else {
        swal('登录之后才能评论喔~','','error');
        
        // confirm("登录之后才能评论喔");
    }

}