/**
 * Created by yi on 2017/5/3.
 */
//切换前后台相应变化
// var gInit = true;
// $(".content").on("click",".switchLogin",function (e) {
//     e.preventDefault();
//     $($(this).attr("href")).show().siblings("form").hide();
//     if(gInit){
//         securityCode1.init("codeBack");
//         gInit = false;
//     }
// });

//    轮播图
$(function () {
    $("#slider").responsiveSlides({
        auto: true,
        pager: true,
        nav: false,
        speed: 500,
        namespace: "callbacks"
    });
});
///////////////////////////////验证码////////////////////////////////


function SecurityCode(elemId) {//elemId为验证码图片id,codeNum为返回的验证码
    this.elemId = elemId;
    this.codeNum = "";
}
SecurityCode.prototype = {
    init: function () {
        var _this=this;
        var obj=document.getElementById(this.elemId);
        this.changeImg(obj);
        obj.onload=function () {
            _this.getCode("/user/getSecurityCode");//此处this已变化
        };
        $("#"+this.elemId).click(function () {
            _this.changeImg(this);
            this.onload=function () {
                _this.getCode("/user/getSecurityCode");
            };
        });
    },
    //获取验证码
    getCode: function (url) {
        var _this = this;
        $.get(url,function (code) {
            _this.codeNum=code;//此处this已变化
        });
    },
    //点击更换验证码图片
    changeImg: function (obj) {
        obj.src="/user/getSecurity?"+Math.random();
    }
}

///////////////////////////////////结束///////////////////////////////////////
//绑定键盘事件
document.onkeydown = function(event){
    var event = (event) ? event : ((window.event) ? window.event : "");
    var key = event.keyCode?event.keyCode:event.which;
    if(key == "13"){
        $(".login-box:not(:hidden)").find(".xsubmit").click();
    }
};
$("input").focus(function () {
    $(this).addClass("input-shadow");
    $(this).prev("span").children().css('color','#477CD8');
});
$("input").blur(function () {
    $(this).removeClass("input-shadow");
    $(this).prev("span").children().css('color','#ffffff');
});
function errorFocus() {
    $(".error-shadow").removeClass("error-shadow");
    $(this).addClass("error-shadow");
};

//使用jquery添加事件
$(".xsubmit").click(function () {
    var _this = this;
    // 按钮文字
    var btnTxt = $(this).html();
    var errorBox=$(this).parent().siblings(".cwts");
    var errorTips=$(errorBox).children(".errorTips");
    var username=$(this).parent().siblings(".uname").children("input[name='username']");
    var password=$(this).parent().siblings(".upwd").children("input[name='password']");
    var inputCode=$(this).parent().siblings(".mycode").children("input[name='code']");
    var code = inputCode.next(".code").attr("id");

    //重置输入框状态
    errorBox.hide();
    if (username.val() == ""){
        errorBox.show();
        username.focus(errorFocus).focus();
        errorTips.html("请输入用户名!");
    }
    else if (password.val() == ""){
        errorBox.show();
        password.focus(errorFocus).focus();
        errorTips.html("请输入密码!");
    }
    // else if(inputCode.val() == "")
    // {
    //     errorBox.show();
    //     errorTips.html("请输入验证码！");
    //     inputCode.focus(errorFocus).focus();
    // }
    //
    // else if(inputCode.val().toUpperCase() != gCode.codeNum.toUpperCase())
    // {
    //     errorBox.show();
    //     errorTips.html("验证码输入有误！");
    //     inputCode.focus(errorFocus).focus();
    //     inputCode.val("");
    //     $("#"+code).click();
    // }
    else {
//            md5加密
        var uname=username.val();
        var upwd=$.md5(password.val());
        var datas={username:uname,password:upwd};
           // console.log(datas);
        var ajaxTimeoutZhike = $.ajax({
            type: 'POST',
            url: '',
            data: datas,//$("form").serialize(),
            timeout: 30000,
            beforeSend:function(XMLHttpRequest){
                $(_this).html("登录中...").prop("disabled",true);
            },
            success: function (obj) {
                //alert(msg);
                if(!obj.result){
                    errorBox.show();
                    password.val("");
                    inputCode.val("");
                    password.focus(errorFocus).focus();
                    errorTips.html("用户名和密码不匹配，请重新输入！");
                    $("#"+code).click();
                }else{
                    window.location.href="/shouye";
                }
            },
            complete:function(XMLHttpRequest,textStatus){
                $(_this).html(btnTxt).prop("disabled",false);
                if(textStatus=='timeout'){//超时,status还有success,error等值的情况
                    ajaxTimeoutZhike.abort();
                    alert("超时");
                }
            }
        });
    }
});
