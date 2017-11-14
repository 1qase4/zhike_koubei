/**
 * Created by Administrator on 2017/10/23.
 */
! function($) {
    "use strict;"
    var Weekpicker = function (element,options) {
        var that = this;
        this.element = $(element);
        this.isInput = this.element.is('input');
        // this.currentYear = options.currentYear && typeof parseInt(options.currentYear) === "number" ? parseInt(options.currentYear) : new Date().getFullYear();
        this.currentYear = new Date().getFullYear();
        this.currentTime = /\u7b2c\d{2}\u5468\d{4}-[01]\d-[0123]\d/.test(this.element.val().trim()) ? this.element.val().trim() : "";
        this.setEndDate = options.setEndDate && typeof parseInt(options.setEndDate) === "number" ? parseInt(options.setEndDate) : new Date().getFullYear();
        this.pickerClass = options.eleClass;
        this.pickerId = options.eleId;
        this.isVisible = false;

        if(this.currentTime){//input中如果有默认值
            this.currentYear = parseInt(this.currentTime.slice(4,8));
        }

        this._attachEvents();
        this.picker = $(template)
            .appendTo('body')
            .on({
                click: $.proxy(this.click, this),
                mousedown: $.proxy(this.mousedown, this)
            });

        $(document).on('mousedown', function(e) {
            // Clicked outside the datetimepicker, hide it
            var target = $(e.target);
            if(target.closest(".wdateBox,.myWeek").length == 0){/*.closest()沿 DOM 树向上遍历，直到找到已应用选择器的一个匹配为止，返回包含零个或一个元素的 jQuery 对象。*/
                that.hide();
            };
        });
        this.update();
        this.hide();
    };
    Weekpicker.prototype = {
        constructor: Weekpicker,
        _events: [],
        _attachEvents: function() {//绑定事件
            this._detachEvents();//开始就要移除事件

            this._events = [
                [this.element, {
                    click: $.proxy(this.show, this)
                }]
            ];

            for(var i = 0, el, ev; i < this._events.length; i++) {
                el = this._events[i][0];
                ev = this._events[i][1];
                el.on(ev);
            }
        },
        _detachEvents: function() {//移除事件
            for(var i = 0, el, ev; i < this._events.length; i++) {
                el = this._events[i][0];
                ev = this._events[i][1];
                el.on(ev);
            }
            this._events = [];
        },
        show: function (e) {//显示

            this.picker.show();
            this.height = this.element.outerHeight();
            //this.update();
            this.place();
            $(window).on('resize', $.proxy(this.place, this));
            if(e) {
                e.stopPropagation();
                e.preventDefault();
            }
            this.isVisible = true;

            /*
             this.element.trigger({
             type: 'show',
             date: this.date
             });
             */
        },
        hide: function(e) {//隐藏
            //if(!this.isVisible) return;
            this.picker.hide();
            $(window).off('resize', this.place);

            //if(!this.isInput) {
            //    $(document).off('mousedown', this.hide);
            //}
            this.isVisible = false;
            /*
             this.element.trigger({
             type: 'hide',
             date: this.date
             });
             */
        },
        place: function () {//确定日期框的位置
            var index_highest = 0;
            $('div').each(function() {
                var index_current = parseInt($(this).css("zIndex"), 10);
                if(index_current > index_highest) {
                    index_highest = index_current;
                }
            });
            var zIndex = index_highest + 10;

            var offset, top, left;
            offset = this.element.offset();
            left = offset.left;
            top = offset.top + this.height;
            this.picker.css({
                top: top,
                left: left,
                zIndex: zIndex
            });
            if(this.pickerClass) this.picker.addClass(this.pickerClass);
            if(this.pickerId) this.picker.attr('id', this.pickerId);
        },
        // getYear:function (n) { //修改当前显示的年份
        //     v = this.currentYear + n;
        //     if(v > this.setEndDate){
        //         v = this.setEndDate;
        //     }
        //     this.currentYear = v;
        // },
        click: function (e) {
            e.stopPropagation();
            e.preventDefault();
            var target = $(e.target).closest('li,p');
            if(target.length == 1) {
                if(target.hasClass("lfArrow")){
                    this.currentYear --;
                    this.update();
                }else if (target.hasClass("rtArrow")){
                    this.currentYear ++;
                    this.update();
                }else if (target.hasClass("week-item")){
                    if (target.hasClass("active")){
                        return false;
                    }
                    target.addClass("active").siblings(".active").removeClass("active");
                    this.currentTime = target.html();
                    this.element.val(this.currentTime);
                    this.element.trigger("change");
                    this.hide();
                }
            }


        },
        update: function() {
            this.picker.find(".ywInput").val(this.currentYear);//更新年份
            this.fill();
        },
        fill: function () {//根据currentYear渲染列表
            var firstDate=new Date(this.currentYear + "-01-01");
            var date = firstDate.getDate();//其实就是1
            // 第一天是星期几
            var firstday = firstDate.getDay();
            date = '0' + (firstday == 0 ? date + 1 : firstday == 1 ? date : date + (8 - firstday));
            //第一个星期一的日期
            var firstD = new Date(this.currentYear + "-01-" + date);

            var html="<li class = 'week-item'>第01周" + this.currentYear + "-01-" + date + "</li>";
            if(this.currentTime == "第01周" + this.currentYear + "-01-" + date){
                html="<li class = 'week-item active'>第01周" + this.currentYear + "-01-" + date + "</li>";
            }

            var newDate;
            var i = 1;
            while(true) {
                newDate = this.getDateByPreNext(firstD,7);
                if(firstD.getFullYear() != this.currentYear){
                    break;
                }
                var a = i + 1;
                a = a < 10 ? '0' + a : a;
                if (this.currentTime === "第"+a+"周"+newDate){
                    html += "<li class = 'week-item active'>第"+a+"周"+newDate+"</li>";
                }else{
                    html += "<li class = 'week-item'>第"+a+"周"+newDate+"</li>";
                }
                i++;
            }
            this.picker.find(".weekInYear").empty().html(html);
            if(this.currentTime && this.currentTime.indexOf(this.currentYear) > 0){//如果input中的值在下拉框中
                var height = this.picker.find("li").outerHeight();
                var n = parseInt(this.currentTime.slice(1,3));
                this.picker.find(".weekInYear").scrollTop((n-2)*height);
            }
        },
        //根据输入的日期计算前后某一天的日期
        getDateByPreNext:function (date,n) {//n可取正负整数
            date.setDate(date.getDate()+n);
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m < 10 ? '0' + m : m;
            var d=date.getDate();
            d = d < 10 ? ('0' + d) : d;
            return y+"-"+m+"-"+d;
        }
    };
    $.fn.weekpicker = function(option) {//当jquery对象调用这个方法时就会重新创建一个Weekpicker对象
        return this.each(function() {
            var $this = $(this),
                options = typeof option == 'object' && option;
            new Weekpicker(this,options);
        });
    };
    $.fn.weekpicker.Constructor = Weekpicker;
    template = "<div class='wdateBox'><div class='wdateTitle'><p class='lfArrow arrowImg'><i class='icon-arrow-left'></i></p><div class='wyearBox'><input type=text readonly class='ywInput'></div><p class='rtArrow arrowImg'><i class='icon-arrow-right'></i></p></div><ul class='weekInYear'></ul></div>";
    $.fn.weekpicker.template = template;
}(window.jQuery);