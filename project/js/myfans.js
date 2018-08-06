$(function() {
    (function loadmyfanlist() {
        var settings = {
            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },
            url: "http://www.bedgasmblog.cn/star/list",
            type: "POST",
            data: {
                "subscribee": sessionStorage.id
            },
            dataType: "json",
            success: function(data) {
                for (var i = 0; i < data.total; i++) {
                    var subscribername = getname(data.data[i].subscriber);
                    var link = "personHome.html?id=" + data.data[i].subscriber;
                    var $li = $("<tr>\
                        <td class=\"article-title\">" + subscribername + "</td>\
                        <td><a href=\"" + link + "\">personHome.html?id=" + data.data[i].subscriber + "</a></td>\
                        </tr>");
                    $("#myfanlist").append($li);
                }
            }
        };
        $.ajax(settings);
    })();

});


function getname(subscriberid) {
    var subscribername;
    var settings = {
        crossDomain: true,
        xhrFields: {
            withCredentials: true
        },
        url: "http://www.bedgasmblog.cn/user/userinfobyid",
        async: false,
        type: "POST",
        data: {
            "id": subscriberid
        },
        dataType: "json",
        success: function(data) {
            subscribername = data.user.nickname;
        }
    };
    $.ajax(settings);
    return subscribername;
}