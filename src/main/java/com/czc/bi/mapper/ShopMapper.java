package com.czc.bi.mapper;

import com.czc.bi.pojo.Shop;
import com.czc.bi.pojo.dto.Simple;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ShopMapper extends BaseMapper<Shop> {

	Shop selectByAccount(String account);
//
	List<String> selectAllShopId();

	List<Simple<String,String>> selectShopsByMerchant(@Param("merchant") String merchant);

	int updateAliPay(@Param("shop") Shop shop);

}