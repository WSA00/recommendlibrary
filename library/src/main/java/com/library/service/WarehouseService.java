package com.library.service;

import com.library.pojo.Warehouse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.library.utils.Result;

public interface WarehouseService extends IService<Warehouse> {

    Result createWarehouse(Warehouse warehouse);

    Result warehousePageSelect(Integer page, Integer pageSize);

    Result selectWarehouseById(Integer id);

    Result UpdateWarehouseById(Integer id, Warehouse warehouse);

    Result deleteWarehouseById(Integer id);

    Result selectWarehouseAndInventoryById(Integer id);

    Result getAllWarehouse();
}
