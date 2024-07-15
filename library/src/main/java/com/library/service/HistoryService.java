package com.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.pojo.History;
import com.library.utils.Result;

/**
* @author ASUS
* @description 针对表【order】的数据库操作Service
* @createDate 2024-04-09 14:59:47
*/
public interface HistoryService extends IService<History> {
    Result historyPageSelect(Integer page, Integer pageSize);

    Result selectHistoryById(Integer id);

    Result UpdateOrderById(Integer id, Integer bid, Integer uid, Integer wid);

    Result createHistory(History history);

    Result deleteHistoryById(Integer id);

//    Result orderPageSelect(Integer page, Integer pageSize);
//
//    Result selectOrderById(Integer id);
//
//    Result UpdateOrderById(Integer id, Integer productId, Integer userId, Integer warehouseId);
//
//    Result createOrder(Order order);
//
//    Result deleteOrderById(Integer id);
}
