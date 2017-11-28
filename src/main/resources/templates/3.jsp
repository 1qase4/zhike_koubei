<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/28
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.8.3/jquery.min.js"></script>
<body>
<div>
    <h2>这是点击的验证码</h2>
    <!-- 这是点击的验证码 -->
    <img id="codeT3" src="../cut/code3?flag=" +Math.random()/>
    <input type="button" value="获取" onclick="getCodeTree();" /> <input
        type="button" value="校验" onclick="cheakOutTree();" /> <select
        id="codeSelect" style="display: none;"></select>
</div>


<script type="text/javascript">


    //////////////////////////////////////////////////鼠标点击校验

    //获取验证码3
    function getCodeTree() {
        $("#codeT3").attr("src","../cut/code3?flag="+Math.random());
    }
    $(function() {
        $("#codeT3").bind(
            "click",
            function(ev) {
                var oEvent = ev || event;
                var number = $("#codeSelect option").length;
                var x = oEvent.pageX;
                var y = oEvent.pageY;
                var img = document.getElementById('codeT3');    //获取图片的原点
                var nodex = getNodePosition(img)[0];//原点x 与原点y
                var nodey = getNodePosition(img)[1];


                var xserver = parseInt(x) - parseInt(nodex);
                var yserver = parseInt(y) - parseInt(nodey);

                if (number >= 4) {
                    return;
                }
                $("#codeSelect").append(
                    "<option value='"+ (parseInt(number)+1) +"'>" + xserver + "_" + yserver
                    + "</option>");
                var oDiv = document.createElement('img');
                oDiv.style.left = (parseInt(x) - 15) + 'px'; // 指定创建的DIV在文档中距离左侧的位置    图片大小30 左右移动15
                oDiv.style.top = (parseInt(y) - 15) + 'px'; // 指定创建的DIV在文档中距离顶部的位置
                oDiv.style.border = '1px solid #FF0000'; // 设置边框
                oDiv.style.position = 'absolute'; // 为新创建的DIV指定绝对定位
                oDiv.style.width = '30px'; // 指定宽度
                oDiv.style.height = '30px'; // 指定高度
                oDiv.src = './img/hj.jpg';
                oDiv.style.opacity = '0.5'; //透明度
                document.body.appendChild(oDiv);
            });

    })

    //校验方法
    //校验验证码1
    function cheakOutTree() {
// var i1 = $("#codeSelect").text();
        var txt = "";
        $("#codeSelect option").each(function (){
            var text = $(this).text();
            if(txt == ""){
                txt = text;
            }else{
                txt = txt + "," + text;
            }
        });
// alert(txt);

        $.ajax({
            type:"post",
            url:"../cut/verifyCode3",
            data : {
                "code" : txt
            },
            cache : false,
            dataType : "JSON",
            success : function(data) {
                alert(data.result);
            }
        });
    }

    function getNodePosition(node) {
        var top = left = 0;
        while (node) {
            if (node.tagName) {
                top = top + node.offsetTop;
                left = left + node.offsetLeft;
                node = node.offsetParent;
            }
            else {
                node = node.parentNode;
            }
        }
        return [left, top];
    }

</script>
</body>
</html>
