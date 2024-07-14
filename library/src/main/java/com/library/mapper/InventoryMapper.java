package com.library.mapper;

import com.library.pojo.Inventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author ASUS
* @description 针对表【inventory】的数据库操作Mapper
* @createDate 2024-04-09 14:59:47
* @Entity com.carsale.pojo.Inventory
*/
public interface InventoryMapper extends BaseMapper<Inventory> {

    int deleteInventoryByProductId(@Param("bid") Integer bid);

    void updateInventoryBySupplierId(@Param("sid") Integer sid);

    void deleteInventoryByWarehouseId(@Param("wid")Integer wid);
}




