package com.czc.bi.mapper;

import com.czc.bi.pojo.Shop;
import com.czc.bi.pojo.Simple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ShopMapper extends BaseMapper {

	Shop selectByAccount(String account);

	List<String> selectAllShopId();

	List<Simple<String,String>> selectShopsByMerchant(@Param("merchant") String merchant);


}