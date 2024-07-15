//package com.library.service.impl;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
////import com.library.mapper.ProductMapper;
//import com.library.mapper.UserMapper;
//import com.library.mapper.WarehouseMapper;
//import com.library.pojo.Order;
//import com.library.response.*;
//import com.library.service.OrderService;
//import com.library.mapper.OrderMapper;
//import com.library.utils.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
///**
//* @author ASUS
//* @description 针对表【order】的数据库操作Service实现
//* @createDate 2024-04-09 14:59:47
//*/
//@Service
//public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
//    implements OrderService{
//
//    @Autowired
//    private OrderMapper orderMapper;
//
////    @Autowired
////    private ProductMapper productMapper;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private WarehouseMapper warehouseMapper;
//
//    @Override
//    public Result orderPageSelect(Integer page, Integer pageSize) {
//        // 获取订单总数
//        Long count = orderMapper.selectOrderCount();
//
//        // 分页查询订单列表
//        List<Order> records = orderMapper.selectOrderPage((page - 1) * pageSize, pageSize);
//
//        List<historyResponse> list = new ArrayList<>();
//        for (Order record : records) {
//            historyResponse orderResponse = new historyResponse();
//            orderResponse.setId(record.getId());
//            System.out.println(record.getId());
////            orderResponse.setBrand(productMapper.selectProductBrandById(record.getProductId()));
////            orderResponse.setModel(productMapper.selectProductModelById(record.getProductId()));
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
//}
//
//
//
//
