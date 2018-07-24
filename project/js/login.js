/*
 *
 * login-register modal
 * Autor: Creative Tim
 * Web-autor: creative.tim
 * Web script: #
 * 
 */
$(document).ready(function () {
    $('.navbar').find('a').each(function () {
            if (this.href == document.location.href || document.location.href.search(this.href) >= 0) {
                $(this).parent().addClass('active'); // this.className = 'active';
            }
        });

});

function showRegisterForm(){
    $('.loginBox').fadeOut('fast',function(){
        $('.registerBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast',function(){
            $('.register-footer').fadeIn('fast');
        });
        $('.modal-title').html('Join us!');
    }); 
    $('.error').removeClass('alert alert-danger').html('');
       
}
function showLoginForm(){
    $('#loginModal .registerBox').fadeOut('fast',function(){
        $('.loginBox').fadeIn('fast');
        $('.register-footer').fadeOut('fast',function(){
            $('.login-footer').fadeIn('fast');    
        });
        
        $('.modal-title').html('请登录');
    });       
     $('.error').removeClass('alert alert-danger').html(''); 
}

function openLoginModal(){
    showLoginForm();
    setTimeout(function(){
        $('#loginModal').modal('show');    
    }, 230);
    
}
function openRegisterModal(){
    showRegisterForm();
    setTimeout(function(){
        $('#loginModal').modal('show');    
    }, 230);
    
}

function loginAjax(){
    /*   Remove this comments when moving to server
    $.post( "/login", function( data ) {
            if(data == 1){
                window.location.replace("/home");            
            } else {
                 shakeModal(); 
            }
        });
    */

/*   Simulate error message from the server   */
     shakeModal();
}

function shakeModal(){
    $('#loginModal .modal-dialog').addClass('shake');
             $('.error').addClass('alert alert-danger').html("Invalid email/password combination");
             $('input[type="password"]').val('');
             setTimeout( function(){ 
                $('#loginModal .modal-dialog').removeClass('shake'); 
    }, 1000 ); 
}

$(function(){
    (function register(){
    // 用户名框失去焦点
    $("#username").blur(function(){
        reg = /^[a-zA-Z0-9_]{4,16}$/;

        if($(this).val()=="")
        {
            $(this).next().html("用户名不能为空！");
            $(this).next().css("display","block");
        }
        else if(!reg.test($("#username").val()))
        {  
            $(this).next().html("格式错误，只能输入数字、字母、下划线的组合！");
            $(this).next().css("display","block");
        }
        else
        {
            $(this).next().css("display","none");
        }
    });
    // 邮箱框失去焦点
    $("#email").blur(function(){
        reg=/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;

        if($(this).val()=="")
        {
            $(this).next().html("邮箱不能为空！");
            $(this).next().css("display","block");
        }
        else if (!reg.test($("#email").val()))
        {
            $(this).next().html("请输入正确的邮箱格式！");
            $(this).next().css("display","block");
        }
        else
        {
            $(this).next().css("display","none");
        }
    });
    // 密码框失去焦点 
    $("#pwd").blur(function(){
        reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[\s\S]{8,30}$/;

        if($(this).val()=="")
        {
            $(this).next().html("密码不能为空！");
            $(this).next().css("display","block");
        }
        else if(!reg.test($("#pwd").val()))
        {  
            $(this).next().html("格式错误，至少包含数字、大小写字母！");
            $(this).next().css("display","block");
        }
        else
        {
            $(this).next().css("display","none");
        }
    });    

    // 确认密码框失去焦点
    $("#password_confirmation").blur(function(){

        if($(this).val()=="")
        {
            $(this).next().html("确认密码不能为空！");
            $(this).next().css("display","block");
        }
        else if($(this).val()!=$("#pwd").val())
        {  
            $(this).next().html("请确保两次输入密码一样！");
            $(this).next().css("display","block");
        }
        else
        {
            $(this).next().css("display","none");
        }
    });
    // 登录账户框失去焦点
    $("#nameoremail").blur(function(){

        if($(this).val()=="")
        {
            $(this).next().html("账户不能为空！");
            $(this).next().css("display","block");
        }
        else
        {
            $(this).next().css("display","none");
        }
    });
    // 登录密码框失去焦点
    $("#loginpwd").blur(function(){

        if($(this).val()=="")
        {
            $(this).next().html("密码不能为空！");
            $(this).next().css("display","block");
        }
        else
        {
            $(this).next().css("display","none");
        }
    });

})();
});
