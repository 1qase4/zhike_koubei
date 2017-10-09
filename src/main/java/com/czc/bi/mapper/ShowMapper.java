package com.czc.bi.mapper;

import com.czc.bi.pojo.Device;
import com.czc.bi.pojo.Simple;
import com.czc.bi.pojo.dto.DeviceStatDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShowMapper {

    List<Simple> selectCity(int argv);

    List<Simple> selectArea(int argv);

    List<Simple> selectSubtype(int argv);

    List<Simple> selectShop(@Param("argv") String argv);

    List<Simple> selectAgent();

    List<Simple> selectMerchant(String argv);

    List<String> selectDevice(@Param("agent") String agent, @Param("merchant") String merchant);

    //Integer selectDeviceStat(@Param("table") String table);
    //最后的数据包时间
    List<Simple> selectDeviceLastTime(@Param("code") String code);
    
    //获取标签
    List<Simple> selectFirstTag();
    
    List<Simple> selectSecondTag(int argv);
    
    List<String> selectSecondTagName(int argv);
    
    
    //获取店铺下的分组
    List<String> selectGroup(@Param("shop") String shop);
    
    //获取分组下的设备号
    List<String> selectGroupDevice(@Param("shop") String shop,@Param("group") String group);
    
    //获得未分组的设备号
    List<String> selectNoGroupDevice(@Param("shop") String shop);
    
    //获取店铺的账号
    List<Simple> selectShop2(@Param("argv") String argv);
    
    //获取分析客流的series 
    List<String> selectSeries(@Param("orderid") String orderid,@Param("type") String type);
    
    //获取历史的分组
    List<Simple> selectHistGroup(@Param("shop") String shop);


    // 获取首页的设备状态信息
    List<DeviceStatDto> selectDeviceStat(@Param("account") String account);

}