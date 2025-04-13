package com.library.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.*;
import com.library.pojo.Book;
import com.library.pojo.Warehouse;
import com.library.response.inventoryResponse;
import com.library.service.WarehouseService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @description 针对表【warehouse】的数据库操作Service实现
*/
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse>
    implements WarehouseService{

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private StockinMapper stockinMapper;

    @Override
    public Result createWarehouse(Warehouse warehouse) {
        warehouseMapper.insert(warehouse);

        Map data = new LinkedHashMap();
        data.put("tip","成功创建仓库");
        data.put("warehouse",warehouse);

        return Result.ok(data);
    }

    @Override
    public Result warehousePageSelect(Integer page, Integer pageSize) {
        Page<Map<String, Object>> mapPage = new Page<>(page, pageSize);

        IPage<Map<String, Object>> result = warehouseMapper.selectMapsPage(mapPage,null);
        List<Map<String, Object>> records = result.getRecords();

        Long count = warehouseMapper.selectCount(null);

        List list = new ArrayList<>();
        for (Map<String, Object> record : records) {
            Warehouse warehouse = new Warehouse();
            warehouse.setId((Integer) record.get("id"));
            warehouse.setLocation((String) record.get("location"));

            list.add(warehouse);
        }

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页"+"共"+pageSize+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("warehouseTotal",count);
        data.put("warehouseList",list);

        return Result.ok(data);
    }

    @Override
    public Result selectWarehouseById(Integer id) {
        Warehouse warehouse = warehouseMapper.selectById(id);

        Map data = new LinkedHashMap();
        data.put("tip","成功获取指定仓库");
        data.put("warehouse",warehouse);

        return Result.ok(data);
    }

    @Override
    public Result UpdateWarehouseById(Integer id, Warehouse warehouse) {
        warehouse.setId(id);

        warehouseMapper.updateById(warehouse);

        Map data = new LinkedHashMap();
        data.put("tip","成功修改仓库信息");
        data.put("warehouse",warehouse);

        return Result.ok(data);
    }

    @Override
    @Transactional
    public Result deleteWarehouseById(Integer id) {
        warehouseMapper.deleteById(id);

        Map data = new LinkedHashMap();
        data.put("tip","成功删除仓库");

        return Result.ok(data);
    }

    @Override
    public Result selectWarehouseAndInventoryById(Integer id) {
        List<Book> list = warehouseMapper.selectWarehouseAndInventoryById(id);

        return Result.ok(list);
    }

    @Override
    public Result getAllWarehouse() {
        List<Warehouse> warehouses = warehouseMapper.getAllWarehouse();
        return Result.ok(warehouses);
    }

    @Override
    public Result WarehouseInventory(Integer id) {

        List<inventoryResponse> list = warehouseMapper.WarehouseInventoryselectByWid(id);

        Map data = new LinkedHashMap();
        data.put("source",list);
        return Result.ok(data);
    }
}




