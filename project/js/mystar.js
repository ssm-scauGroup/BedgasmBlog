$(function() {
    (function loadmystarlist() {
        var settings = {
            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },
            url: "http://www.bedgasmblog.cn/star/list",
            type: "POST",
            data: {
                "subscriber": sessionStorage.id
            },
            dataType: "json",
            success: function(data) {
                // console.log(data.articles[0].title);
                // console.log(data.articles[1].title);
                for (var i = 0; i < data.total; i++) {
                    var subscribeename = getname(data.data[i].subscribee);
                  
                    var link = "personHome.html?id=" + data.data[i].subscribee;
                    var $li = $("<tr>\
                        <td style=\"display: none;\">" + data.data[i].subscribee + "</td>\
                        <td><input type=\"checkbox\" class=\"input-control\" name=\"checkbox[]\" value=\"\" /></td>\
                        <td class=\"article-title\">" + subscribeename + "</td>\
                        <td><a href=\"" + link + "\">personHome.html?id=" + data.data[i].subscribee + "</a></td>\
                        <td><a href=\"#\" onclick=\"itemdelete(this)\">取消订阅</a></td>\
                    </tr>");
                    $("#mystarlist").append($li);
                }
            }
        };
        $.ajax(settings);
    })();

    (function loadstarcount() {
        var settings = {
            url: "http://www.bedgasmblog.cn/star/list",
            type: "POST",
            data: {
                "subscriber": sessionStorage.id
            },
            dataType: "json",
            success: function(data) {
                $("#mystarcount").text(data.total);
            }
        };
        $.ajax(settings);
    })();

});


function getname(subscribeeid) {
    var subscribeename;
    var settings = {
        crossDomain: true,
        xhrFields: {
            withCredentials: true
        },
        url: "http://www.bedgasmblog.cn/user/userinfobyid",
        async: false,
        type: "POST",
        data: {
            "id": subscribeeid
        },
        dataType: "json",
        success: function(data) {
            subscribeename = data.user.nickname;
        }
    };
    $.ajax(settings);
    return subscribeename;
}


function itemdelete(clickeditem) {
    console.log($(clickeditem.parentNode.parentNode).children().eq(0).text());
    var r = confirm("确认取消关注？");
    if (r == true) {
        var subscribeeid = $(clickeditem.parentNode.parentNode).children().eq(0).text();
        var settings = {
            url: "http://www.bedgasmblog.cn/star/unsubscribe",
            async: false,
            type: "POST",
            data: {
                "subscriber": sessionStorage.getItem("id"),
                "subscribee": subscribeeid
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


function selecteddelete() {
    var count = $("input[name*=checkbox]:checked").length;
    r = confirm("确认删除所选中的" + count + "项？（此操作不可逆）");
    if (r == true) {
        $("input[name*=checkbox]:checked").each(function() {
            var subscribeeid = $(this.parentNode.parentNode).children().eq(0).text();
            var settings = {
                url: "http://www.bedgasmblog.cn/star/unsubscribe",
                async: false,
                type: "POST",
                data: {
                    "subscriber": sessionStorage.getItem("id"),
                    "subscribee": subscribeeid
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
