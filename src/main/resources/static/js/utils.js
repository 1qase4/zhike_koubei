/**
 * Created by Administrator on 2017/8/18.
 */
//根据当日计算前后某一天的日期
function getDateByNow(n) {//n可取正负整数
    var date=new Date();
    date.setDate(date.getDate()+n);
    var y=date.getFullYear();
    var m=date.getMonth()+1;
    m = m < 10 ? '0' + m : m;
    var d=date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y+"年"+m+"月"+d+"日";
}
//根据当月计算前后某个月
function getMonthByNow(n) {//n可取正负整数(上个月或下个月)
    var date=new Date();
    date.setDate(date.getDate()+n);
    var y=date.getFullYear();
    var m=date.getMonth()+1;
    m = m < 10 ? '0' + m : m;
    return y+"年"+m+"月";
}
//根据输入的日期计算前后某一天的日期
function getDateByDate(day,n) {//n可取正负整数
    var date=new Date(day);
    date.setDate(date.getDate()+n);
    var y=date.getFullYear();
    var m=date.getMonth()+1;
    m = m < 10 ? '0' + m : m;
    var d=date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y+"-"+m+"-"+d;
}
//获取某一日所在周的星期一日期和本周是今年的第几周(参数为日期对象)
function getWeek(dateobj) {
    // var today = new Date();
    //今天日期
    var date = dateobj.getDate();
    //今天星期几
    var day = dateobj.getDay();
    //这个星期一的日期(毫秒数)
    var monday = day == 0 ? today.setDate(date - 6) : dateobj.setDate(date - (day - 1));
    //这个星期一的日期
    var _monday = new Date(monday);
    var newDate;
    var count = 1;
    while(true){
        newDate = weekChoose.getDateByPre(_monday,-7);
        if(_monday.getFullYear()!=new Date().getFullYear()){
            break;
        }
        count ++;
    }
    //第几周
    count = count < 10 ? '0'+count : count;
    var mon = new Date(monday);
    // mon.setDate(mon.getDate()+n);
    var y=mon.getFullYear();
    var m=mon.getMonth()+1;
    m = m < 10 ? '0' + m : m;
    var d=mon.getDate();
    d = d < 10 ? ('0' + d) : d;
    //输出星期一的日期
    var formatMon = y+"-"+m+"-"+d;
    return {
        thisYear:y,
        monDate:formatMon,
        order:count
    }
}

