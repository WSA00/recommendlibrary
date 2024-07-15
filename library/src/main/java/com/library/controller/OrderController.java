//package com.library.controller;
//
//import com.library.pojo.Order;
//import com.library.service.OrderService;
//import com.library.utils.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@CrossOrigin
//@RequestMapping("order")
//public class OrderController {
//
//    @Autowired
//    private OrderService orderService;
//
//    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
//    @GetMapping("")
//    public Result orderPageSelect(Integer page, Integer pageSize){
//        return orderService.orderPageSelect(page,pageSize);
//    }
//
//    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
//    @GetMapping("{id}")
//    public Result selectOrderById(@PathVariable Integer id){
//        return orderService.selectOrderById(id);
//    }
//
//    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
//    @PatchMapping("{id}")
//    public Result UpdateOrderById(@PathVariable Integer id,@RequestBody Integer productId, Integer userId, Integer warehouseId){
//        return orderService.UpdateOrderById(id,productId,userId,warehouseId);
//    }
//
//    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
//    @PostMapping("")
//    public Result createOrder(@RequestBody Order order){
//        return orderService.createOrder(order);
//    }
//
//    @PreAuthorize("hasAnyAuthority('USER','ROOT')")
//    @DeleteMapping("{id}")
//    public Result deleteOrderById(@PathVariable Integer id){
//        return orderService.deleteOrderById(id);
//    }
//
//
//}
