$(function () {
    loadlinkcount();
    loadlinklist();
       
});

function loadlinkcount () {
    // body... 
    var settings = {
            url: "http://www.bedgasmblog.cn/links/list",
            type: "POST",
            data: {},
            dataType: "json",
            success: function (data) {
                $("#linkcount").text(data.total);
            }
        };
        $.ajax(settings);
}

function loadlinklist () {
    // body... 
    var settings = {
            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },
            url: "http://www.bedgasmblog.cn/links/list",
            type: "POST",
            data: {},
            dataType: "json",
            success: function (data) {
                for (var i = 0; i < data.total; i++) {
                    var $li = $("<tr>\
                        <td style=\"display: none;\">"+ data.links[i].id + "</td>\
                        <td><input type=\"checkbox\" class=\"input-control\" name=\"checkbox[]\" value=\"\" /></td>\
                        <td class=\"article-title\">"+ data.links[i].linkname + "</td>\
                        <td><a href=\""+ data.links[i].linkurl + "\">" + data.links[i].linkurl + "</a></td>\
                        <td><a href=\"update-article.html\">修改</a> <a onclick=\"itemdelete(this)\">删除</a></td>\
                    </tr>");
                    $("#linkslist").append($li);
                }
            }
        };
        $.ajax(settings);
}

function itemdelete(clickeditem) {
    var r = confirm("确认删除友情链接？");
    if (r == true) {
        var linkid = $(clickeditem.parentNode.parentNode).children().eq(0).text();
        console.log(linkid);
        var settings = {
            url: "http://www.bedgasmblog.cn/links/delete",
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
    if (r == true) {
        $("input[name*=checkbox]:checked").each(function () {
            var linkid = $(this.parentNode.parentNode).children().eq(0).text();
            var settings = {
                url: "http://www.bedgasmblog.cn/links/delete",
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
                    location.reload();
                }
            };
            $.ajax(settings);
        });

    }
}

function addlink() {
    var linkname = $("#link-name").val();
    var linkurl = $("#link-URL").val();
    var settings = {
        crossDomain: true,
        xhrFields: {
            withCredentials: true
        },
        url: "http://www.bedgasmblog.cn/links/save",
        type: "POST",
        data: {
            'linkname': linkname,
            'linkurl': linkurl
        },
        dataType: "json",
        success: function(data) {
            location.reload();
        }
    };
    $.ajax(settings);
}