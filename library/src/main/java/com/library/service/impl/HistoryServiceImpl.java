package com.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.BookMapper;
import com.library.mapper.HistoryMapper;
import com.library.pojo.Book;
import com.library.mapper.UserMapper;
import com.library.mapper.WarehouseMapper;
import com.library.pojo.History;
import com.library.response.orderResponse;
import com.library.response.updateOrderResponse;
import com.library.service.HistoryService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* 针对表【history】的数据库操作Service实现
*/
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History>
    implements HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

//    @Override
//    public Result orderPageSelect(Integer page, Integer pageSize) {
//        // 获取订单总数
//        Long count = orderMapper.selectOrderCount();
//
//        // 分页查询订单列表
//        List<Order> records = orderMapper.selectOrderPage((page - 1) * pageSize, pageSize);
//
//        List<orderResponse> list = new ArrayList<>();
//        for (Order record : records) {
//            orderResponse orderResponse = new orderResponse();
//            orderResponse.setId(record.getId());
//            System.out.println(record.getId());
//            orderResponse.setBrand(productMapper.selectProductBrandById(record.getProductId()));
//            orderResponse.setModel(productMapper.selectProductModelById(record.getProductId()));
//            orderResponse.setUser(userMapper.selectUserNameById(record.getUserId()));
//            orderResponse.setPhone(userMapper.selectUserPhoneById(record.getUserId()));
//            orderResponse.setWarehouse(warehouseMapper.selectLocationById(record.getWarehouseId()));
//            orderResponse.setCreatetime(record.getCreatetime());
//            list.add(orderResponse);
//        }
//
//        Map data = new LinkedHashMap();
//        data.put("tip","成功获取第"+page+"页"+pageSize+"共条数据");
//        data.put("page",page);
//        data.put("count",pageSize);
//        data.put("pageTotal",(int)Math.ceil(count/pageSize));
//        data.put("orderTotal",count);
//        data.put("orderList",list);
//
//        return Result.ok(data);
//    }
//
//
//    @Override
//    public Result selectOrderById(Integer id) {
//        Order order = orderMapper.selectById(id);
//        Map data = new LinkedHashMap();
//        data.put("tip","成功获取订单");
//        data.put("order",order);
//
//        return Result.ok(data);
//
//    }
//
//    //没用上
//    @Override
//    public Result UpdateOrderById(Integer id, Integer productId, Integer userId, Integer warehouseId) {
//        Order order = new Order();
//        order.setId(id);
//        order.setProductId(productId);
//        order.setUserId(userId);
//        order.setWarehouseId(warehouseId);
//
//        updateOrderResponse updateOrderResponse = new updateOrderResponse(order);
//
//        Map data = new LinkedHashMap();
//        data.put("tip","成功修改订单");
//        data.put("order",updateOrderResponse);
//
//        return Result.ok(data);
//    }
//
//    @Override
//    public Result createOrder(Order order) {
//
//        orderMapper.insertOrder(order);
//
//        Map data = new LinkedHashMap();
//        data.put("tip","成功创建订单");
//        data.put("order", order);
//
//        return Result.ok(data);
//    }
//
//    @Override
//    public Result deleteOrderById(Integer id) {
//        Order order = orderMapper.selectOrderById(id);
//        orderMapper.deleteOrderById(id);
//
//        Map data = new LinkedHashMap();
//        data.put("tip","成功删除订单");
//        data.put("result",new updateOrderResponse(order));
//
//        return Result.ok(data);
//    }
//
}




