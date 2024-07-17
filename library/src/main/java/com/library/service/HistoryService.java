package com.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.pojo.History;
import com.library.utils.Result;

import java.util.Date;

/*
*针对表【history】的数据库操作Service
*/
public interface HistoryService extends IService<History> {
    Result historyPageSelect(Integer page, Integer pageSize);

    Result selectHistoryById(Integer id);

    Result UpdateHistoryById(Integer id);

    Result createHistory(History history);

    Result deleteHistoryById(Integer id);

    Result continueHistoryById(Integer id);

    Result dealHistoryById(Integer id);


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
