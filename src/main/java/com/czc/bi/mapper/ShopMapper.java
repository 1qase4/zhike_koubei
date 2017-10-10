package com.czc.bi.mapper;

import com.czc.bi.pojo.Shop;
import com.czc.bi.pojo.ShowShop;
import com.czc.bi.pojo.Simple;
import com.czc.bi.pojo.StayShow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {

	int insert(Shop record);

	// 查询名字是否存在
	int selectNameNums(String name);
	//查询账号是否存在
	int selectAccountNums(String account);

	List<ShowShop> selectShowShop(@Param("agent") String agent, @Param("merchant") String merchant);

	// 获取某一天店铺的客流信息
	List<Simple> selectShopFlow(@Param("tables") List<String> tables, @Param("date") String date);

	// 获取一段时间店铺的客流信息
	List<Simple> selectShopFlowSection(@Param("tables") List<String> dev, @Param("begin") String begin,
                                       @Param("end") String end);

	// 获取店铺客户逗留信息
	List<StayShow> selectStayShow(@Param("tables") List<String> tables, @Param("begin") String begin,
                                  @Param("end") String end, @Param("limit") Integer limit);

	// 获取店铺潜在客户
	// List<StayShow> selectPotential(@Param("tables") List<String> tables,
	// @Param("start") int start, @Param("limit") int limit);
	List<StayShow> selectPotential(String shop);

	// 获取店铺客户逗留信息记录条数
	Integer selectStayShowCount(@Param("tables") List<String> dev, @Param("begin") String begin,
                                @Param("end") String end);

	// 获取店铺潜在客户记录条数
	Integer selectPotentialCount(String shop);

	// 获取店铺潜在客户的导出信息
	List<StayShow> selectPotentialExport(String name);

	Shop selectByAccount(String account);

	// 更加code修改shop信息
	int updateShopByCode(Shop shop);

	// 根据店铺名找到对应的账户
	List<String> selectByName(String name);

	String selectByName2(String name);

	// 获取商户下的店铺
	List<Shop> selectByMerchant(String merchant);

	// 根据店铺名找到对应的手机号
	String selectPhone(String name);

	// 根据店铺账号找到对应的手机号
	String selectPhoneByAcct(String account);

	// 根据店铺账号找出店铺名
	String selectByAccount2(String account);

	// 根据店铺名找出对应 的营业时间
	String selectShoptime(@Param("name") String name);

	// 获取商户下的店铺
	List<String> selectMerchentShop(@Param("account") String account);

}