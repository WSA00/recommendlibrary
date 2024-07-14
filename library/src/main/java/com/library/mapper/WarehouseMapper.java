package com.library.mapper;

import com.library.pojo.Warehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*
*  针对表【warehouse】的数据库操作Mapper
*/
public interface WarehouseMapper extends BaseMapper<Warehouse> {

//    List<Warehouse> selectWarehouseByProductId(@Param("productId") Integer productId);

//    List<Product> selectWarehouseAndInventoryById(@Param("warehouseId") Integer warehouseId);

    Warehouse selectWarehouseById(@Param("wid") Integer wid);

    List<Warehouse> getAllWarehouse();

    String selectLocationById(@Param("wid") Integer wid);
}




