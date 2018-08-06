window.onload = function() {
    console.log("role"+sessionStorage.role);
    if(sessionStorage.role==1){
        console.log(sessionStorage.role);
        getUserTotalPage();
        listUserAllArticleAjax(userPagenumber);
    }
    else{
        console.log(sessionStorage.role);
        $("#searcharea").css("display","block");
        getTotalPage();
        listAllArticleAjax(adminPagenumber);
    }
    
    
};



var adminPagenumber=1;
var adminPagetotal;
var userPagenumber=1;
var userPagetotal;
var userid=sessionStorage.getItem("id");

function backArticlePage(){
    if(adminPagenumber>1 && sessionStorage.role==0){
        $("#backpage").removeClass('disable');
        adminPagenumber-=1;
        listAllArticleAjax(adminPagenumber);
    }
    else if(adminPagenumber==1 && sessionStorage.role==0) {
        $("#backpage").addClass('disable');
    }
    else if (userPagenumber>1 && sessionStorage.role==1){
        $("#backpage").removeClass('disable');
        userPagenumber-=1;
        listUserAllArticleAjax(userPagenumber);
    }
    else if (userPagenumber==1 && sessionStorage.role==1){
        $("#backpage").addClass('disable');
    }
}

function nextArticlePage(){
    
    if(adminPagenumber < adminPagetotal && sessionStorage.role==0){
        adminPagenumber+=1;
        listAllArticleAjax(adminPagenumber);
    }
    else if (adminPagenumber == adminPagetotal && sessionStorage.role==0){
        $("#nextpage").addClass('disable');
    }
    else if(userPagenumber < userPagetotal && sessionStorage.role==1){
        userPagenumber+=1;
        listUserAllArticleAjax(userPagenumber);
    }
    else if (userPagenumber == userPagetotal && sessionStorage.role==1){
        $("#nextpage").addClass('disable');
    }
    
}

function jumpArticlePage(){
    var jumppage = $('#jumppage').val();
    if (jumppage<=adminPagetotal && sessionStorage.role==0) {
        console.log("跳到"+jumppage);
        adminPagenumber=jumppage;
        listAllArticleAjax(jumppage); 
    }
    else if (jumppage<=userPagetotal && sessionStorage.role==1){
        console.log("跳到"+jumppage);
        userPagenumber=jumppage;
        listUserAllArticleAjax(jumppage); 
    }
    else if (jumppage>adminPagetotal || jumppage>userPagetotal)
        alert("超过页数！");
}

//用户界面列出所有文章
function listUserAllArticleAjax(page) {
    console.log("userid" + sessionStorage.id);
    var settings = {
        url: "http://www.bedgasmblog.cn/article/list",
        type: "POST",
        async: false,
        crossDomain: "true",
        xhrFields: {
            withCredentials: "true"
        },
        data: {
            "page":page,
            "rows":10,
            "authorid": sessionStorage.id
        },
        dataType: "json",
        success: function(res) {
            var detail = "";
            var articleDiv = document.getElementById("allarticle");
            var title , typename , tags , replyCount , releaseDate , url ,nickname , articleid,murl;
            for (var key in res['posts']) {
                articleid = res['posts'][key]['id'];
                typename = getArticleType(res['posts'][key]['blogtypeid']);
                url = "./article.html?id=" + res['posts'][key]['id'];
                title = res['posts'][key]['title'];
                replyCount = res['posts'][key]['replyCount'];
                releaseDate = res['posts'][key]['releaseDate'];
                tags = res['posts'][key]['tags'];
                nickname = res['posts'][key]['user']['nickname'];
                // var aid = $(clickeditem.parentNode.parentNode).children().eq(0).text();
                murl="./updateArticle.html?id="+articleid;
                // var url = "./article.html?id=" + data.posts[i].author;
                if(res['posts'][key].hasOwnProperty("tags")){
                    detail +=`<tr>
                    <td style="display: none;">${articleid}</td>
                    <td><input type="checkbox" class="input-control" name="checkbox[]" value="" /></td>
                    <td class="article-title"><a href="${url}">${title}</a></td>
                    <td>${nickname}</td>
                    <td>${typename}</td>
                    <td class="hidden-sm">${tags}</td>
                    <td class="hidden-sm">${replyCount}</td>
                    <td>${releaseDate}</td>
                    <td><a href="${murl}">修改</a><a onclick="itemdelete(this)" id="articleid"> 删除</a></td>
                  </tr>`;
                }
                else
                {
                    detail +=`<tr>
                    <td style="display: none;">${articleid}</td>
                    <td><input type="checkbox" class="input-control" name="checkbox[]" value="" /></td>
                    <td class="article-title"><a href="${url}">${title}</a></td>
                    <td>${typename}</td>
                    <td class="hidden-sm">暂无标签</td>
                    <td class="hidden-sm">${replyCount}</td>
                    <td>${releaseDate}</td>
                    <td><a href="${murl}">修改</a><a onclick="itemdelete(this)" id="articleid"> 删除</a></td>
                  </tr>`;
                }
            }
            allarticle.innerHTML = detail;
            console.log("总页数"+adminPagetotal);
            console.log("success get");
            console.log("当前页"+adminPagenumber);
        },

        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);
}

//管理员界面列出所有文章 分页显示
function getArticleType(blogtypeid) {
    var typename;
    var settings = {
        url: "http://www.bedgasmblog.cn/category/detail",
        async: false,
        type: "POST",
        data: {
            "id": blogtypeid
        },
        dataType: "json",
        success: function (data) {
            typename = data.category.typename;
        }
    };
    $.ajax(settings);
    // console.log("gettype ok");
    return typename;
}

function listAllArticleAjax(page){

    var settings = {
        url: "http://www.bedgasmblog.cn/article/list",
        crossDomain: "true",
        xhrFields: {
            withCredentials: "true"
        },
        type: "POST",
        async: false,
        data: {
            'page': page,
            'rows': "10"
        }, 
        dataType: "json",
        success: function(res) {
            var detail = "";
            var articleDiv = document.getElementById("allarticle");
            var title , typename , tags , replyCount , releaseDate , url, nickname , articleid;
            for (var key in res['posts']) {
                // console.log(data.posts[i].blogtypeid);
                typename = getArticleType(res['posts'][key]['blogtypeid']);
                articleid = res['posts'][key]['id'];
                url = "./article.html?id=" + res['posts'][key]['id'];
                title = res['posts'][key]['title'];
                replyCount = res['posts'][key]['replyCount'];
                releaseDate = res['posts'][key]['releaseDate'];
                tags = res['posts'][key]['tags'];
                nickname = res['posts'][key]['user']['nickname'];
                if(res['posts'][key].hasOwnProperty("tags")){
                    detail +=`<tr>
                    <td style="display: none;">${articleid}</td>
                    <td><input type="checkbox" class="input-control" name="checkbox[]" value="" /></td>
                    <td class="article-title"><a href="${url}">${title}</a></td>
                    <td>${nickname}</td>
                    <td>${typename}</td>
                    <td class="hidden-sm">${tags}</td>
                    <td class="hidden-sm">${replyCount}</td>
                    <td>${releaseDate}</td>
                    <td><a onclick="itemdelete(this)" id="articleid"> 删除</a></td>
                  </tr>`;
                }
                else
                {
                    detail +=`<tr>
                    <td style="display: none;">${articleid}</td>
                    <td><input type="checkbox" class="input-control" name="checkbox[]" value="" /></td>
                    <td class="article-title"><a href="${url}">${title}</a></td>
                    <td>${typename}</td>
                    <td class="hidden-sm">暂无标签</td>
                    <td class="hidden-sm">${replyCount}</td>
                    <td>${releaseDate}</td>
                    <td><a onclick="itemdelete(this)" id="articleid"> 删除</a></td>
                  </tr>`;
                }
            }
            allarticle.innerHTML = detail;
            console.log("总页数"+adminPagetotal);
            console.log("success get");
            console.log("当前页"+adminPagenumber);
        },

        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);
}

//管理员界面获取所有文章总数
function getTotalPage(){
    var settings = {
        url: "http://www.bedgasmblog.cn/article/getTotal",
        async:false,
        type: "POST",
        data: {
        },
        dataType: "json",
        success: function (data) {
            if (data.total%10==0){
                adminPagetotal=data.total/10;
                $("#totalpage").text('共'+adminPagetotal+'页');
            }
            else{
                adminPagetotal= Math.floor(data.total/10+1);
                $("#totalpage").text('共'+adminPagetotal+'页');
            }
            console.log("所有文章共"+data.total+"篇");
            console.log("所有文章共"+adminPagetotal+"页");

        }
    };
    $.ajax(settings);
}
//用户界面获取该用户文章总数
function getUserTotalPage(){
    var settings = {
        url: "http://www.bedgasmblog.cn/article/getTotal",
        async:false,
        type: "POST",
        data: {
            "authorid":userid
        },
        dataType: "json",
        success: function (data) {
            if (data.total%10==0){
                userPagetotal=data.total/10;
                $("#totalpage").text('共'+userPagetotal+'页');
            }
            else{
                userPagetotal=Math.floor(data.total/10+1);
                $("#totalpage").text('共'+userPagetotal+'页');
            }
            console.log(userid+"该用户共"+data.total+"篇");
            console.log(userid+"该用户共"+userPagetotal+"页");
        }
    };
    $.ajax(settings);
}

function searcharticle(){
        var searchkey=$("#searchkey").val();
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
            var detail = "";
            var articleDiv = document.getElementById("allarticle");
            var title , typename , tags , replyCount , releaseDate , url;
            for (var key in res['articles']) {
                typename = getArticleType(res['articles'][key]['blogtypeid']);
                url = "./article.html?id=" + res['articles'][key]['id'];
                title = res['articles'][key]['title'];
                replyCount = res['articles'][key]['replyCount'];
                releaseDate = res['articles'][key]['releaseDate'];
                tags = res['articles'][key]['tags'];
                if(res['articles'][key].hasOwnProperty("tags")){
                    detail +=`<tr>
                    <td><input type="checkbox" class="input-control" name="checkbox[]" value="" /></td>
                    <td class="article-title"><a href="${url}">${title}</a></td>
                    <td>${typename}</td>
                    <td class="hidden-sm">${tags}</td>
                    <td class="hidden-sm">${replyCount}</td>
                    <td>${releaseDate}</td>
                    <td><a onclick="itemdelete(this)" id="articleid"> 删除</a></td>
                  </tr>`;
                }
                else
                {
                    detail +=`<tr>
                    <td><input type="checkbox" class="input-control" name="checkbox[]" value="" /></td>
                    <td class="article-title"><a href="${url}">${title}</a></td>
                    <td>${typename}</td>
                    <td class="hidden-sm">暂无标签</td>
                    <td class="hidden-sm">${replyCount}</td>
                    <td>${releaseDate}</td>
                    <td><a onclick="itemdelete(this)" id="articleid">删除</a></td>
                  </tr>`;
                }
            }
            allarticle.innerHTML = detail;
            console.log("总页数"+adminPagetotal);
            console.log("success search");
            console.log("当前页"+adminPagenumber);
        },

        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);
}

function itemdelete(clickeditem) {
    var r = confirm("确认删除文章？");
    if (r == true) {
        var linkid = $(clickeditem.parentNode.parentNode).children().eq(0).text();
        
        console.log(linkid);
        var settings = {
            url: "http://www.bedgasmblog.cn/article/delete",
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
                url: "http://www.bedgasmblog.cn/article/delete",
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


function modifyAtricle(){
     var linkid = $(clickeditem.parentNode.parentNode).children().eq(0).text();
     var url="./write-article2.html?id="+linkid;
     window.onload.href(url);
}