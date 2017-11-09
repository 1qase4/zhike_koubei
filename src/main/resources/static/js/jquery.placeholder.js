/**
 * Created by Administrator on 2016/12/27.
 */
$(function () {
    //兼容不支持placeholder的浏览器[ie浏览器，并且10以下均采用替代方式处理]
    if ((navigator.appName == "Microsoft Internet Explorer") && (document.documentMode < 10 || document.documentMode == undefined)) {
        var $placeholder = $("input[placeholder]");
        for (var i = 0; i < $placeholder.length; i++) {
            if ($placeholder.eq(i).attr("type") == "password") {
                $placeholder.eq(i).siblings("label").text($placeholder.eq(i).attr("placeholder")).show()
            } else {
                $placeholder.eq(i).val($placeholder.eq(i).attr("placeholder")).css({"color": "#ccc"})
            }
        };
        $placeholder.focus(function () {
            if ($(this).attr("type") == "password") {
                $(this).siblings("label").hide()
            } else {
                if ($(this).val() == $(this).attr("placeholder")) {
                    $(this).val("").css({"color": "#333"})
                }
            }
        }).blur(function () {
            if ($(this).attr("type") == "password") {
                if ($(this).val() == "") {
                    $(this).siblings("label").text($(this).attr("placeholder")).show()
                }
            } else {
                if ($(this).val() == "") {
                    $(this).val($(this).attr("placeholder")).css({"color": "#ccc"})
                }
            }
        });
    }
});