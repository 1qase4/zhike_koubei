package com.czc.bi.mapper;

import com.czc.bi.pojo.Shop;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopMapper {

	Shop selectByAccount(String account);

}