window.onload=function(){
    getUserTotalPage();
	listUserAllArticleAjax(userPagenumber);

};


var adminPagenumber = 1;
var adminPagetotal;
var userPagenumber = 1;
var userPagetotal;
var userid = sessionStorage.getItem("id");


function backArticlePage() {
    if (adminPagenumber > 1 && sessionStorage.role == 0) {
        $("#backpage").removeClass('disable');
        adminPagenumber -= 1;
        listAllArticleAjax(adminPagenumber);
    } else if (adminPagenumber == 1 && sessionStorage.role == 0) {
        $("#backpage").addClass('disable');
    } else if (userPagenumber > 1 && sessionStorage.role == 1) {
        $("#backpage").removeClass('disable');
        userPagenumber -= 1;
        listUserAllArticleAjax(userPagenumber);
    } else if (userPagenumber == 1 && sessionStorage.role == 1) {
        $("#backpage").addClass('disable');
    }
}

function nextArticlePage() {

    if (adminPagenumber < adminPagetotal && sessionStorage.role == 0) {
        adminPagenumber += 1;
        listAllArticleAjax(adminPagenumber);
    } else if (adminPagenumber == adminPagetotal && sessionStorage.role == 0) {
        $("#nextpage").addClass('disable');
    } else if (userPagenumber < userPagetotal && sessionStorage.role == 1) {
        userPagenumber += 1;
        listUserAllArticleAjax(userPagenumber);
    } else if (userPagenumber == userPagetotal && sessionStorage.role == 1) {
        $("#nextpage").addClass('disable');
    }

}

function jumpArticlePage() {
    var jumppage = $('#jumppage').val();
    if (jumppage <= adminPagetotal && sessionStorage.role == 0) {
        console.log("跳到" + jumppage);
        adminPagenumber = jumppage;
        listAllArticleAjax(jumppage);
    } else if (jumppage <= userPagetotal && sessionStorage.role == 1) {
        console.log("跳到" + jumppage);
        userPagenumber = jumppage;
        listUserAllArticleAjax(jumppage);
    } else if (jumppage > adminPagetotal || jumppage > userPagetotal)
        swal("超过页数！","","warning");
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
            "page": page,
            "rows": 10,
            "authorid": sessionStorage.id
        },
        dataType: "json",
        success: function(res) {
            var detail = "";
            var articleDiv = document.getElementById("allarticle");
            var title, typename, tags, replyCount, releaseDate, url, nickname, articleid, murl;
            for (var key in res['posts']) {
                articleid = res['posts'][key]['id'];
                typename = getArticleType(res['posts'][key]['blogtypeid']);
                url = "./article.html?id=" + res['posts'][key]['id'];
                title = res['posts'][key]['title'];
                replyCount = res['posts'][key]['replyCount'];
                releaseDate = res['posts'][key]['releaseDate'];
                tags = res['posts'][key]['tags'];
                nickname = res['posts'][key]['user']['nickname'];
                murl = "./updateArticle.html?id=" + articleid;
                    detail += `<tr>
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
            articleDiv.innerHTML = detail;
            console.log("总页数" + userPagetotal);
            console.log("success get");
            console.log("当前页" + userPagenumber);
        },

        error: function(res) {
            console.log("error");
        }
    };
    $.ajax(settings);
}

//用户界面获取该用户文章总数
function getUserTotalPage() {
    var settings = {
        url: "http://www.bedgasmblog.cn/article/getTotal",
        async: false,
        type: "POST",
        data: {
            "authorid": userid
        },
        dataType: "json",
        success: function(data) {
            if (data.total % 10 == 0) {
                userPagetotal = data.total / 10;
                $("#totalpage").text('共' + userPagetotal + '页');
            } else {
                userPagetotal = Math.floor(data.total / 10 + 1);
                $("#totalpage").text('共' + userPagetotal + '页');
            }
            console.log(userid + "该用户共" + data.total + "篇");
            console.log(userid + "该用户共" + userPagetotal + "页");
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

                    success: function(data) {
                        if (data.success == true) {
                            $(clickeditem.parentNode.parentNode).remove();
                            swal(
                                '删除！',
                                '文章已经被删除。',
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


function selecteddelete() {
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
                var count = $("input[name*=checkbox]:checked").length;
                var isdelete = true;
                $("input[name*=checkbox]:checked").each(function() {
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

                        success: function(data) {

                            if (data.success == true) {
                                swal(
                                '删除！',
                                '文章已经被删除。',
                                'success'
                            );
                            setTimeout("location.reload()", 1000);
                            }
                        }
                    };
                    $.ajax(settings);
                });

                if (isdelete) {
                    $("input[name*=checkbox]:checked").each(function() {
                        $(this.parentNode.parentNode).remove();
                        location.reload();
                    });
                }
            }
        } else {

        }
    })

}


function modifyAtricle() {
    var linkid = $(clickeditem.parentNode.parentNode).children().eq(0).text();
    var url = "./write-article2.html?id=" + linkid;
    window.onload.href(url);
}

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
        success: function(data) {
            typename = data.category.typename;
        }
    };
    $.ajax(settings);
    // console.log("gettype ok");
    return typename;
}