package com.library.controller;

import com.library.pojo.Warehouse;
import com.library.service.WarehouseService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @GetMapping("{id}/WarehouseInventory")
    public Result WarehouseInventory(@PathVariable Integer id){
        return warehouseService.WarehouseInventory(id);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @PostMapping("")
    public Result createWarehouse(@RequestBody Warehouse warehouse){
        return warehouseService.createWarehouse(warehouse);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @GetMapping("")
    public Result warehousePageSelect(Integer page, Integer pageSize){

        if (page == null && pageSize == null) {
            return warehouseService.getAllWarehouse();
        }else {
            return warehouseService.warehousePageSelect(page,pageSize);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @GetMapping("{id}")
    public Result selectWarehouseById(@PathVariable Integer id){
        return warehouseService.selectWarehouseById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @PatchMapping("{id}")
    public Result UpdateWarehouseById(@PathVariable Integer id, @RequestBody Warehouse warehouse){
        return warehouseService.UpdateWarehouseById(id,warehouse);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @DeleteMapping("{id}")
    public Result deleteWarehouseById(@PathVariable Integer id){
        return warehouseService.deleteWarehouseById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @GetMapping("{id}/inventory")
    public Result selectWarehouseAndInventoryById(@PathVariable Integer id){
        return warehouseService.selectWarehouseAndInventoryById(id);
    }

}
