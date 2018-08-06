$(function () {
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
            success: function (data) {
                for (var i = 0; i < data.total; i++) {
                    var $li = $("<tr>\
                        <td style=\"display: none;\">"+ data.categories[i].id + "</td>\
                        <td><input type=\"checkbox\" class=\"input-control\" name=\"checkbox[]\" value=\"\" /></td>\
                        <td><input class=\"article-title\" value=\""+ data.categories[i].typename +"\"></td>\
                        <td name=\"total\">"+ data.categories[i].typecount + "</td>\
                        <td><a href=\"update-article.html\">修改</a> <a onclick=\"itemdelete(this)\" rel=\"6\">删除</a></td>\
                        </tr>");
                    $("#categorylist").append($li);
                }
            }
        };
        $.ajax(settings);
    })();
});


function itemdelete(clickeditem) {
    var r = confirm("确认删除该项？");
    if (r == true) {
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
                location.reload();
            }
        };
        $.ajax(settings);
    }
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
        success: function (data) {
            location.reload();
        }
    };
    $.ajax(settings);
}


function selecteddelete() {
    var count = $("input[name*=checkbox]:checked").length;
    r = confirm("确认删除所选中的" + count + "项？（此操作不可逆）");
    if (r == true) {
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
                    location.reload();
                }
            };
            $.ajax(settings);
        });

    }
}



function logout() {
    var settings = {
        url: "http://www.bedgasmblog.cn/user/logout",
        type: "POST",
        data: {},
        dataType: "json",
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