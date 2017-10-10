package com.czc.bi.util;

import com.czc.bi.pojo.Simple;
import com.czc.bi.pojo.echarts.pojo.Option;

import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : Option 转换器
 * @date : 2016年6月22日 上午11:36:39
 * @version: V1.0
 */

public interface OptConvert {
    public Option getOption();

    public void setXString(List<String> x) throws Exception;

    public void addRecord(String legend, List<Number> record) throws Exception;

    public void addRecord(String legend, Number[] record) throws Exception;
}
