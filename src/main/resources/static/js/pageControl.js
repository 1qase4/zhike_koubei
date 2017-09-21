
(function(){
    var Page = (function(){
        var Page = function(obj){
            return fn.init(obj);
        }
        var fn = Page.prototype = {
            init:function(obj){
                if(obj.pageSize>obj.pageCount){
                    alert("分页数小于总页数不合法");
                    return;
                }
                this.pageCount = obj.pageCount;//总页数
                this.pageSize = obj.pageSize || 4;//分页大小 一开始最多显示几页 默认7
                this.pageNow = obj.pageNow || 1;//当前页
                this.containerId = obj.id;//装页码的容器
                this.container = document.getElementById(this.containerId);
                fn.drawPage();
                return this;
            },
            drawPage:function(){
                this.container.innerHTML = "";
                var front = document.createElement("a");
                var next = document.createElement("a");
                this.acontainer = document.createElement("span");

                front.innerHTML = "上一页";
                next.innerHTML = "下一页";
                front.className = "front";
                next.className = "next";
                this.container.appendChild(front);//上一页 放在container 中

                front.onclick = function(){
                    fn.frontOrNext(this,true);
                }
                next.onclick = function(){
                    fn.frontOrNext(this,false);
                }
                this.front = front;
                this.next = next;
                if(this.pageCount<= this.pageSize){
                    front.className+=" disable";
                    // next.className+=" disable";
                    for( var i=0;i<this.pageCount;i++ ){
                        var a = document.createElement("a");
                        a.innerHTML = i+1;
                        a.onclick = function(){
                            Page.alert(this.innerHTML);
                            Page.select(this,"select");
                        }
                        this.acontainer.appendChild(a);//页码放在acontainer 中
                        if(i+1 == this.pageNow)a.className = "select";//当前页样式为select
                    }
                }else{
                    /*-------------------------------总页数多余pageSize-------------------*/
                    var etc2 = document.createElement("a");
                    etc2.innerHTML = "...";
                    etc2.className = "etc";
                    if(this.pageNow <= this.pageSize -1 ){
                        for( var i=0;i<this.pageSize;i++ ){
                            var a = document.createElement("a");
                            a.innerHTML = i+1;
                            a.onclick = function(){
                                fn.pageNow = this.innerHTML;
                                Page.alert(this.innerHTML);
                                Page.select(this,"select");
                                fn.check();
                                if(fn.pageNow>=fn.pageSize-1){
                                    fn.reset();
                                }
                                etc2.className = "etc";
                            }
                            this.acontainer.appendChild(a);//页码放在acontainer 中
                            if(i+1 == this.pageNow)a.className = "select";//当前页样式为select
                        }
                        fn.check();
                        this.acontainer.appendChild(etc2);
                    }else{
                        fn.reset();
                    }
                }
                var jumpContainer = document.createElement("span");//放置页码跳转的容器
                var span1 = document.createElement("span");
                var span2 = document.createElement("span");
                var inputBox = document.createElement("input");
                var button = document.createElement("input");
                inputBox.value = 1;
                button.value = "确定";
                button.type  = "button";
                button.className = "page-btn";
                inputBox.className = "page-box";
                this.inputBox = inputBox;
                button.onclick = function(){
                    var page = fn.inputBox.value.trim();
                    fn.jump(page);
                }
                span1.innerHTML = "共"+this.pageCount+"页";
                span2.innerHTML = "跳转到";
                span1.className = "jumpContainer";
                span2.className = "jumpContainer";
                span2.appendChild(inputBox);
                span2.appendChild(button);
                jumpContainer.appendChild(span1);
                jumpContainer.appendChild(span2);
                this.container.appendChild(this.acontainer);//绘制页码
                this.container.appendChild(next);//下一页 放在container
                this.container.appendChild(jumpContainer);
            },
            frontOrNext:function(node,isFront){
                if(node.className.indexOf("disable")!=-1){
                    return;//如果上一页下一页显示为不可点击，退出
                }
                if(isFront){
                    this.pageNow--;
                    Page.alert(this.pageNow);
                    //上一页
                }else{
                    this.pageNow++;
                    Page.alert(this.pageNow);
                    //下一页
                }
                this.judge();
            },
            judge:function(){
                if(this.pageNow - this.start>2){
                    this.reset();
                }else{
                    this.drawPage();
                }
            },
            jump:function(page){
                if(page>this.pageCount){
                    alert("超出范围");
                    return;
                }
                this.pageNow = page;
                Page.alert(this.pageNow);
                if(this.pageNow - this.start>2){
                    this.reset();
                }else{
                    this.drawPage();
                }
            },
            check:function(){
                if(this.pageNow <=1 ){
                    this.front.className += " disable";
                }else{
                    this.front.className = "front";
                }
            },
            reset:function(){
                this.acontainer.innerHTML="";
                var a1 = document.createElement("a");
                var a2 = document.createElement("a");
                var etc1 = document.createElement("a");
                var etc2 = document.createElement("a");
                a1.innerHTML = "1";
                a2.innerHTML = "2";
                etc1.innerHTML = "...";
                etc2.innerHTML = "...";
                etc1.className = etc2.className = "etc";
                this.front.className = "front";
                a2.onclick = a1.onclick = function(){
                    fn.pageNow = this.innerHTML;
                    Page.alert(this.innerHTML);
                    Page.select(this,"select");
                    fn.drawPage();
                    return;
                }
                this.acontainer.appendChild(a1);
                this.acontainer.appendChild(a2);
                this.acontainer.appendChild(etc1);
                var start;
                /*调整*/
                start = this.pageNow - Math.ceil((this.pageSize-2)/2)+1;
                (start >= this.pageCount - (this.pageSize - 2)+1)?(start = (this.pageCount - (this.pageSize - 2)+1),this.isEnd = true): (this.isEnd=false);//起始页码判断
                this.start = start;
                for( var i = 0 ; i<this.pageSize-2;i++ ){
                    var a = document.createElement("a");
                    a.innerHTML = start+i;
                    a.onclick = function(){
                        fn.pageNow = this.innerHTML;
                        Page.alert(this.innerHTML);
                        Page.select(this,"select");
                        fn.judge();
                    }
                    this.acontainer.appendChild(a);//页码放在acontainer 中
                    if((start+i)== this.pageNow)a.className = "select";
                }
                (!this.isEnd) && this.acontainer.appendChild(etc2);//如果没到最后一组页码显示...
                (this.pageNow == this.pageCount)  && (this.next.className += " disable");//到了最后一页不显示下一页
                (this.pageNow!=this.pageCount)&& (this.next.className = "next");
            }
        }

        Page.select = function(node,className){
            var aArray = fn.acontainer.getElementsByTagName("a"),//获取所有aContainer下的页码
                aLength =  aArray.length;//页码长度
            for( var i=0;i<aLength;i++ ){
                aArray[i].className ="";//清除样式
            }
            node.className = className;//当前页赋予select样式
        }

        Page.alert = function(page){
            console.log("第"+page+"页");

            // 请求
        }


        return Page;
    })();
    window.Page = Page;
})();