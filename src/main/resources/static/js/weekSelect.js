/**
 * Created by Administrator on 2017/7/26.
 */
    //    选择周的日期框
var weekChoose={
        template:"<div class='wdateBox'><div class='wdateTitle'><div class='lfArrow arrowImg'><i class='icon-arrow-left'></i></div><div class='wyearBox'><input type=text readonly class='ywInput'></div><div class='rtArrow arrowImg'><i class='icon-arrow-right'></i></div></div><ul class='weekInYear'></ul></div>",
        init: function () {
            var _this=this;
            $("body").append(this.template);
            $(".wdateBox").on("mouseup","li",function(){
                var val=$(this).html();
                // var v=val.slice(4,8)+"年"+val.slice(0,4);
                $(".activeInput").val(val);
                var d=val.slice(4)+val.slice(1,3);
                // console.log(d);
                // $(".activeInput").attr("title",d);
                //手动触发input的change事件
                $(".activeInput").trigger("change");
                $(".myWeek").removeClass("activeInput");
                $(".wdateBox").hide();
            });
//            获取焦点时出现周下拉框
            $(".myWeek").focus(function(){
                $(this).addClass("activeInput");
//       获取当前元素相对于body的位置
                var left=getElementViewLeft(this);
                var top=getElementViewTop(this);
                var height=this.offsetHeight;
//        得到周选择框的确定位置
                $(".wdateBox").css({left:left,top:top+height});
//        获取当前年份并赋值给 .ywInput
                var now=new Date();
                var y=now.getFullYear();
                $(".ywInput").val(y);
//        渲染周下拉表
                _this.getWeekList(y,$(".weekInYear"));
                $(".wdateBox").show();
            });
//            翻年份绑定点击事件
            $(".arrowImg").click(function () {
                var year;
                var input;
                if($(this).hasClass("lfArrow")){
                    input=$(this).next().children("input");
                    year=_this.getYear(input,-1);
                }else{

                    input=$(this).prev().children("input");
                    year=_this.getYear(input,1);
                }
                input.val(year);
                var obj=$(this).parent().next();
                _this.getWeekList(year,obj);
            });
            //点击空白处隐藏
            $(document).bind("click",function(e){
                var target  = $(e.target);
                if(target.closest(".wdateBox,.myWeek").length == 0){/*.closest()沿 DOM 树向上遍历，直到找到已应用选择器的一个匹配为止，返回包含零个或一个元素的 jQuery 对象。*/
                    $(".wdateBox").hide();
                };
                e.stopPropagation();
            });
        },
        //渲染周选择列表的方法
        getWeekList: function (y,jqueryObj){
            jqueryObj.empty();
            var firstDate=new Date(y+"-01-01");
            var date=firstDate.getDate();//其实就是1
            // 第一天是星期几
            var firstday=firstDate.getDay();
            date=firstday==0?date+1:firstday==1?date:date+(8-firstday);
            date = '0' + date;
            //第一个星期一的日期
            var firstD=new Date(y+"-01-"+date);
            var html="<li>第01周"+y+"-01-"+date+"</li>";
            var newDate;
            var i = 1;
            while(true) {
                newDate=this.getDateByPre(firstD,7);
                if(firstD.getFullYear()!=y){
                    break;
                }
                var a=i+1;
                a=a<10?'0'+a:a;
                html+="<li>第"+a+"周"+newDate+"</li>";
                i++;
            }
            jqueryObj.html(html);
        },
//        上下翻年份的方法
        getYear:function (input,n) {
            var v=parseInt(input.val())+n;
            var now=new Date();
            if(v>now.getFullYear()){
                v=now.getFullYear();
            }
            return v;
        },
        //根据输入的日期计算前后某一天的日期
        getDateByPre:function (date,n) {//n可取正负整数
            date.setDate(date.getDate()+n);
            var y=date.getFullYear();
            var m=date.getMonth()+1;
            m = m < 10 ? '0' + m : m;
            var d=date.getDate();
            d = d < 10 ? ('0' + d) : d;
            return y+"-"+m+"-"+d;
        }
    };
weekChoose.init();
//    获取某节点对象的位置
function getElementViewLeft(element){
    var actualLeft = element.offsetLeft;
    var current = element.offsetParent;
    while (current !== null){
        actualLeft += current.offsetLeft;
        current = current.offsetParent;
    }
    if (document.compatMode == "BackCompat"){
        var elementScrollLeft=document.body.scrollLeft;
    } else {
        var elementScrollLeft=document.documentElement.scrollLeft;
    }
    return actualLeft-elementScrollLeft;
}
function getElementViewTop(element){
    var actualTop = element.offsetTop;
    var current = element.offsetParent;
    while (current !== null){
        actualTop += current. offsetTop;
        current = current.offsetParent;
    }
    if (document.compatMode == "BackCompat"){
        var elementScrollTop=document.body.scrollTop;
    } else {
        var elementScrollTop=document.documentElement.scrollTop;
    }
    return actualTop-elementScrollTop;
}
(function ($) {
    $.fn.weekChoose = function() {
        var template = "<div class='wdateBox'><div class='wdateTitle'><div class='lfArrow arrowImg'><i class='icon-arrow-left'></i></div><div class='wyearBox'><input type=text readonly class='ywInput'></div><div class='rtArrow arrowImg'><i class='icon-arrow-right'></i></div></div><ul class='weekInYear'></ul></div>";
        $("body").append(template);

    };
})(jQuery);