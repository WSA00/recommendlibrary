package com.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.pojo.Inventory;
import com.library.service.InventoryService;
import com.library.mapper.InventoryMapper;
import org.springframework.stereotype.Service;

/**
 * @description 针对表【inventory】的数据库操作Service实现
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory>
        implements InventoryService{

}