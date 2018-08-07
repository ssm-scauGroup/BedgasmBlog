$(function () {
    backtotop();
   loadPerArticleAjax();
    loadUserinfoAjax();     
    isFollowed();
    if(sessionStorage.id==userid){
        $("#star").css("display","none");
    }
    else if (sessionStorage.id!=userid){
        $("#star").css("display","block");
        
    }
    
});

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

function pHlogout() {
    var settings = {
        url: "http://www.bedgasmblog.cn/user/logout",
        type: "POST",
        data: {},
        dataType:"json",
        success: function (data) {
            if (data.success == true) {
                console.log("退出！");
                sessionStorage.clear();
                window.location.href = 'index.html';
            }
        },
    };
    $.ajax(settings);
}

var userid;
function loadPerArticleAjax(){
        var regex = /.*?id=([0-9]*)/ig;
            var detailUrl = window.location.href;
            var detailId = regex.exec(detailUrl)[1];
            userid = detailId;
            console.log("uid"+userid);
            if (detailId){
                console.log(detailId);
                var html = $.ajax({
                async: true,
                  crossDomain:"true",
        xhrFields:{
            withCredentials:"true"
        },
                url: "http://www.bedgasmblog.cn/article/author",
                type: "post",
                data: {'id': detailId},
                dataType: "json",
                success: function (res) {
                    console.log("id:"+detailId);
                     var articleDiv = document.getElementById("articleDiv");
                    var detail = "";
                    var title, summary, url, releaseDate, username, commentCount,authorUrl;
                    for (var key in res['articles']) {
                        title = res['articles'][key]['title'];
                        summary = res['articles'][key]['summary'];
                        url = "./article.html?id=" + res['articles'][key]['id'];
                        authorUrl="./personHome.html?id=" + res['articles'][key]['author'];
                        releaseDate = res['articles'][key]['releaseDate'];
                        commentCount = res['articles'][key]['replyCount'];
                        username=res['articles'][key]['user']['nickname'];
                        detail +=
                            `<article class="blog-post">
                                <div class="blog-post-body">
                                    <h2><a href="${url}">${title}</a></h2>
                                    <div class="post-meta"><span>by <a href="${authorUrl}">${username}</a></span>/<span><i class="fa fa-clock-o"></i>${releaseDate}</span>/<span><i class="fa fa-comment-o"></i> <a href="#">${commentCount}</a></span></div>
                                    <p>${summary}</p>
                                    <div class="read-more"><a href="${url}">继续阅读</a></div>
                                </div>
                            </article>`;
                    }

                    articleDiv.innerHTML = detail;
                    console.log("successget");
                 },
                error: function (res) {
                    console.log("error");
                }
            })
            }
            else{
                alert("没有id");
            }
}

function loadUserinfoAjax(){
        var settings = {
        url: "http://www.bedgasmblog.cn/user/userinfobyid",
          crossDomain:"true",
        xhrFields:{
            withCredentials:"true"
        },
        type: "POST",
        data: {
            'id':userid
        }, 
        dataType: "json",
        success: function(res) {
            var hotarticle = document.getElementById("hotarticle");
            var detail = "";
            var nickname,profile,signature;
            nickname = res['user']['nickname'];
            profile = res['user']['profile'];
            signature = res['user']['signature'];
            $("#pH_nickname").text(nickname);
            $("#pH_signature").text(signature);
            // var u= "data:image/png;base64,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";

            $("#headimage").css("background",`url(${profile}) no-repeat`);
            $("#headimage").css("background-position",`center center`);
            $("#headimage").css("background-size",`100%`);
            console.log("in success");
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

// var followed=false;
function isFollowed(){
    var settings = {
        url: "http://www.bedgasmblog.cn/star/list",
          crossDomain:"true",
        xhrFields:{
            withCredentials:"true"
        },
        async:false,
        type: "POST",
        data: {
            "subscriber": sessionStorage.id
        }, 
        dataType: "json",
        success: function(res) {
            for (var key in res['data']) {
                if(userid == res['data'][key]['subscribee']){
                    // followed = true;
                    console.log("followed");
                    $("#star").html("<span class='glyphicon glyphicon-star-empty' id='plus'></span>&nbsp已订阅");
                    $("#star").removeClass("btn-warning");
                    $("#star").addClass("btn-default");
                    break;
                }
                // else{
                //     console.log("notfollow");
                // }
            }

            
        },
        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);
}

function followorquit () {
    if($("#star").hasClass("btn-warning")){
        follow();
    }
    else if ($("#star").hasClass("btn-default")){
        quit();
    }
}

function follow() {
    var settings = {
        url: "http://www.bedgasmblog.cn/star/subscribe",
        crossDomain: "true",
        xhrFields: {
            withCredentials: "true"
        },
        async: false,
        type: "POST",
        data: {
            "subscriber": sessionStorage.id,
            "subscribee":userid
        },
        dataType: "json",
        success: function(res) {
            if (res.success) {
                swal('订阅成功','','success');
                $("#star").html("<span class='glyphicon glyphicon-star-empty' id='plus'></span>&nbsp已订阅");
                $("#star").removeClass("btn-warning");
                $("#star").addClass("btn-default");
            }
            else {
                swal('订阅失败');
            }

        },
        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);
}

function quit () {
    // body... 
    var settings = {
        url: "http://www.bedgasmblog.cn/star/unsubscribe",
        crossDomain: "true",
        xhrFields: {
            withCredentials: "true"
        },
        async: false,
        type: "POST",
        data: {
            "subscriber": sessionStorage.id,
            "subscribee":userid
        },
        dataType: "json",
        success: function(res) {
            if (res.success) {
               swal('取消订阅成功','','success');
                $("#star").html("<span class='glyphicon glyphicon-plus' id='plus'></span>&nbsp订阅");
                $("#star").removeClass("btn-default");
                $("#star").addClass("btn-warning");
            }
            else {
                swal('订阅失败');
            }

        },
        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);
}