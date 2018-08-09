/*
 *
 * login-register modal
 * Autor: Creative Tim
 * Web-autor: creative.tim
 * Web script: #
 * 
 */

function showRegisterForm() {
    $('.loginBox').fadeOut('fast', function () {
        $('.registerBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast', function () {
            $('.register-footer').fadeIn('fast');
        });
        $('.modal-title').html('Join us!');
    });
    $('.error').removeClass('alert alert-danger').html('');

}

function showLoginForm() {
    $('#loginModal .registerBox').fadeOut('fast', function () {
        $('.loginBox').fadeIn('fast');
        $('.register-footer').fadeOut('fast', function () {
            $('.login-footer').fadeIn('fast');
        });

        $('.modal-title').html('请登录');
    });
    $('.error').removeClass('alert alert-danger').html('');
}

function openLoginModal() {
    showLoginForm();
    setTimeout(function () {
        $('#loginModal').modal('show');
    }, 230);

}

function openRegisterModal() {
    showRegisterForm();
    setTimeout(function () {
        $('#loginModal').modal('show');
    }, 230);

}

function logout() {
    var settings = {
        url: "http://www.bedgasmblog.cn/user/logout",
        crossDomain: "true",
        xhrFields: {
            withCredentials: "true"
        },
        type: "POST",
        data: {},
        dataType: "json",
        success: function (data) {
            if (data.success == true) {
                console.log("退出！");
                location.href = 'index.html';
                location.reload();
                sessionStorage.clear();

            }
        },
    };
    $.ajax(settings);
}

function register() {
    var settings = {
        url: "http://www.bedgasmblog.cn/user/register",
        crossDomain: "true",
        xhrFields: {
            withCredentials: "true"
        },
        type: "POST",
        data: {
            username: $("#username").val(),
            password: $("#pwd").val(),
            email: $("#email").val()
        },
        dataType: "json",
        success: function (data) {
            if (data.success == true) {
                swal("注册成功！您现在可以登录了！", "", "success");
                setTimeout(function () { location.reload() }, 1500);
            }
        },
    };
    if ($(".form-control1").val() == "") {
        alert("信息没填完");
        return false;
    } else if (($(".error1").css("display") != "none") || ($(".error2").css("display") != "none") || ($(".error3").css("display") != "none") || ($(".error4").css("display") != "none")) {
        alert("信息填写有误");
        return false;
    } else {
        $.ajax(settings);
    }
}

function loginAjax() {
    if ($("#validatecode").val() == "") {
        $(".error7").html("验证码不能为空！点击验证码图片刷新");
        $(".error7").css("display", "block");
        refreshvalidatecode();
        return false;
    } else if (!checkvalidatecode()) {
        $(".error7").html("验证码错误,请重新输入！可点击验证码图片刷新");
        $(".error7").css("display", "block");
        refreshvalidatecode();
        return false;
    } else {
        $(".error7").css("display", "none");
    }

    var settings = {
        url: "http://www.bedgasmblog.cn/user/login",
        crossDomain: "true",
        xhrFields: {
            withCredentials: "true"
        },
        type: "POST",
        data: {
            username: $("#nameoremail").val(),
            password: $("#loginpwd").val()
        },
        // dataType:"json",
        success: function (data) {
            if (data.msg == "登录成功") {
                window.location.reload();
                $("#login-button").css("display", "none");
                $("#signin-button").css("display", "none");
                $('#loginModal').modal('hide');
                $("#account").css("display", "block");
                // $("#accountname").html(data.user.nickname);
                console.log("success");
                sessionStorage.setItem("email", data.user.email);
                sessionStorage.setItem("id", data.user.id);
                sessionStorage.setItem("nickname", data.user.nickname);
                sessionStorage.setItem("profile", data.user.profile);
                sessionStorage.setItem("role", data.user.role);
                sessionStorage.setItem("signature", data.user.signature);
                sessionStorage.setItem("username", data.user.username);
                $("#accountname").html(sessionStorage.getItem("nickname"));

            } else {
                $("#loginfail").html("用户名或密码错误！");
            }
        },
    };
    $.ajax(settings);
}

function shakeModal() {
    $('#loginModal .modal-dialog').addClass('shake');
    $('.error').addClass('alert alert-danger').html("Invalid email/password combination");
    $('input[type="password"]').val('');
    setTimeout(function () {
        $('#loginModal .modal-dialog').removeClass('shake');
    }, 1000);
}

$(function () {
    (function loadpage() {
        // console.log("load......");
        if (sessionStorage.getItem("nickname") != null) {
            $("#login-button").css("display", "none");
            $("#signin-button").css("display", "none");
            $('#loginModal').modal('hide');
            $("#account").css("display", "block");
            $("#accountname").html(sessionStorage.getItem("nickname"));
        }
    })();
    (function register() {
        // 用户名框失去焦点
        $("#username").blur(function () {
            reg = /^[a-zA-Z0-9_]{4,16}$/;

            if ($(this).val() == "") {
                $(this).next().html("用户名不能为空！");
                $(this).next().css("display", "block");
            } else if (!reg.test($("#username").val())) {
                $(this).next().html("格式错误，只能输入数字、字母、下划线的组合！");
                $(this).next().css("display", "block");
            } else {
                $(this).next().css("display", "none");
                checkusername();
            }
        });

        $("#nickname").blur(function () {
            reg = /^[a-zA-Z0-9_]{4,16}$/;

            if ($(this).val() == "") {
                $(this).next().html("昵称不能为空！");
                $(this).next().css("display", "block");
            } else if (!reg.test($("#nickname").val())) {
                $(this).next().html("昵称只能由大小写字母，下划线组成!");
                $(this).next().css("display", "block");
            } else {
                $(this).next().css("display", "none");
            }
        });

        // 邮箱框失去焦点
        $("#email").blur(function () {
            reg = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;

            if ($(this).val() == "") {
                $(this).next().html("邮箱不能为空！");
                $(this).next().css("display", "block");
            } else if (!reg.test($("#email").val())) {
                $(this).next().html("请输入正确的邮箱格式！");
                $(this).next().css("display", "block");
            } else {
                $(this).next().css("display", "none");
                if ($(this).hasClass('form-control1')) {
                    checkemail();
                }

            }
        });
        // 密码框失去焦点 
        $("#pwd").blur(function () {
            reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[\s\S]{8,30}$/;

            if ($(this).val() == "") {
                $(this).next().html("密码不能为空！");
                $(this).next().css("display", "block");
            } else if (!reg.test($("#pwd").val())) {
                $(this).next().html("格式错误，至少包含数字、大小写字母！");
                $(this).next().css("display", "block");
            } else {
                $(this).next().css("display", "none");
            }
        });

        // 确认密码框失去焦点
        $("#password_confirmation").blur(function () {

            if ($(this).val() == "") {
                $(this).next().html("确认密码不能为空！");
                $(this).next().css("display", "block");
            } else if ($(this).val() != $("#pwd").val()) {
                $(this).next().html("请确保两次输入密码一样！");
                $(this).next().css("display", "block");
            } else {
                $(this).next().css("display", "none");
            }
        });
        // 登录账户框失去焦点
        $("#nameoremail").blur(function () {

            if ($(this).val() == "") {
                $(this).next().html("账户不能为空！");
                $(this).next().css("display", "block");
            } else {
                $(this).next().css("display", "none");
            }
        });
        // 登录密码框失去焦点
        $("#loginpwd").blur(function () {

            if ($(this).val() == "") {
                $(this).next().html("密码不能为空！");
                $(this).next().css("display", "block");
            } else {
                $(this).next().css("display", "none");
            }
        });
        //验证码框聚焦
        $("#validatecode").focus(function () {
            $(".error7").css("display", "none");
        });

        //验证码框失去焦点
        // $("#validatecode").blur(function () {

        //     if ($(this).val() == "") {
        //         $(".error7").html("验证码不能为空！点击验证码图片刷新");
        //         $(".error7").css("display", "block");
        //     } else if (!checkvalidatecode()) {
        //         $(".error7").html("验证码错误,请重新输入！可点击验证码图片刷新");
        //         $(".error7").css("display", "block");
        //     } else {
        //         $(".error7").css("display", "none");
        //     }
        // });
    })();

    (function testfunction() {
        var settings = {
            url: "http://www.bedgasmblog.cn/user/userinfo",
            crossDomain: "true",
            xhrFields: {
                withCredentials: "true"
            },
            type: "POST",
            data: {},
            dataType: "json",
            success: function (data) {
                console.log("userinfo:" + data.success);
            }
        };
        $.ajax(settings);
    })();


});

function checkusername() {
    var settings = {
        url: "http://www.bedgasmblog.cn/user/isValid",
        crossDomain: "true",
        xhrFields: {
            withCredentials: "true"
        },
        type: "POST",
        data: {
            username: $("#username").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.isValid == false) {
                $("#username").next().html("用户名已被注册！");
                $("#username").next().css("display", "block");
            }
        },
    };
    $.ajax(settings);
}

function checkemail() {
    var settings = {
        url: "http://www.bedgasmblog.cn/user/isValid",
        crossDomain: "true",
        xhrFields: {
            withCredentials: "true"
        },
        type: "POST",
        data: {
            email: $("#email").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.isValid == false) {
                $("#email").next().html("邮箱已被使用！");
                $("#email").next().css("display", "block");
            }
        },
    };
    $.ajax(settings);
}

var timer = null;
// function loginClick(){
//     $("#loginModal").modal('hide');
//     $(location).attr('href', 'signin.html');
// }

function refreshvalidatecode() {
    var time = new Date().getTime();
    $("#validatecodeimg").attr("src", "http://www.bedgasmblog.cn/user/validatecode?" + time);
    $("#validatecode").val("");
}

function checkvalidatecode() {
    var isTrue = false;
    var settings = {
        "async": false,
        crossDomain: true,
        url: "http://www.bedgasmblog.cn/user/checkcode",
        Type: "POST",
        "headers": {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        xhrFields: {
            withCredentials: true
        },
        "data": {
            "vcode": $("#validatecode").val()
        },
        success: function (data) {
            isTrue = data.success;
        }
    };
    $.ajax(settings);
    return isTrue;
}