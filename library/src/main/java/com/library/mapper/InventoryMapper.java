package com.library.mapper;

import com.library.pojo.Inventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @description 针对表【inventory】的数据库操作Mapper
*/
public interface InventoryMapper extends BaseMapper<Inventory> {

    int deleteInventoryByProductId(@Param("bid") Integer bid);

    void updateInventoryBySupplierId(@Param("sid") Integer sid);

    void deleteInventoryByWarehouseId(@Param("wid")Integer wid);
}




