$(function() {
    loadlinkcount();
    loadlinklist();

});

function loadlinkcount() {
    // body... 
    var settings = {
        url: "http://www.bedgasmblog.cn/links/list",
        type: "POST",
        data: {},
        dataType: "json",
        success: function(data) {
            $("#linkcount").text(data.total);
        }
    };
    $.ajax(settings);
}

function loadlinklist() {
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
        success: function(data) {
            for (var i = 0; i < data.total; i++) {
                var $li = $("<tr>\
                        <td style=\"display: none;\">" + data.links[i].id + "</td>\
                        <td><input type=\"checkbox\" class=\"input-control\" name=\"checkbox[]\" value=\"\" /></td>\
                        <td class=\"article-title\">" + data.links[i].linkname + "</td>\
                        <td><a href=\"" + data.links[i].linkurl + "\">" + data.links[i].linkurl + "</a></td>\
                        <td><a onclick=\"itemdelete(this)\">&nbsp;&nbsp;&nbsp;删除</a></td>\
                    </tr>");
                $("#linkslist").append($li);
            }
        }
    };
    $.ajax(settings);
}

function itemdelete(clickeditem) {
    // var r = confirm("确认删除友情链接？");
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

                    success: function(data) {
                        if (data.success == true) {
                            swal(
                                '删除！',
                                '友链已经被删除。',
                                'success'
                            );
                            setTimeout("location.reload()", 1000);
                        }
                        console.log(data);
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
    var msg = "确认删除所选中的" + count + "项？（此操作不可逆）";
    // r = confirm("确认删除所选中的" + count + "项？（此操作不可逆）");

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

                        success: function(data) {
                            if (data.success == true) {
                                swal(
                                    '删除！',
                                    '友链已经被删除。',
                                    'success'
                                );
                                setTimeout("location.reload()", 1000);
                            }
                        }
                    };
                    $.ajax(settings);
                });
            }
        } else {

        }
    })

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