package com.library.mapper;

import com.library.pojo.Book;
import com.library.pojo.Warehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.library.response.inventoryResponse;

import java.util.List;

/**
*
*  针对表【warehouse】的数据库操作Mapper
*/
public interface WarehouseMapper extends BaseMapper<Warehouse> {

    List<Warehouse> selectWarehouseByBid(@Param("bid") Integer bid);

    List<Book> selectWarehouseAndInventoryById(@Param("wid") Integer wid);

    Warehouse selectWarehouseById(@Param("wid") Integer wid);

    List<Warehouse> getAllWarehouse();

    String selectLocationById(@Param("wid") Integer wid);

    List<inventoryResponse> WarehouseInventoryselectByWid(Integer id);
}




