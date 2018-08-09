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

    swal({
        title: '确定取消订阅吗？',
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
                        swal(
                            '删除！',
                            '取消订阅成功。',
                            'success'
                        );
                        setTimeout("location.reload()", 1000);
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
    var msg = "确认取消订阅所选中的" + count + "项？（此操作不可逆）";
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
                                swal(
                                    '删除！',
                                    '取消订阅成功。',
                                    'success'
                                );
                                setTimeout("location.reload()", 1000);

                            }
                        };
                        $.ajax(settings);
                    });

                });

            }
        } else {

        }
    })
}