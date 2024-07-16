package com.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.BookMapper;
import com.library.mapper.HistoryMapper;
import com.library.mapper.UserMapper;
import com.library.mapper.WarehouseMapper;
import com.library.pojo.History;
import com.library.response.historyResponse;
import com.library.service.HistoryService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public Result historyPageSelect(Integer page, Integer pageSize) {

        if (page == null || page <= 0) {
            page = 1; // 默认第一页
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 15; // 默认每页15条记录
        }

        // 获取订单总数
        Long count = historyMapper.selectHistoryCount();

        // 分页查询订单列表
        List<History> records = historyMapper.selectHistoryPage((page - 1) * pageSize, pageSize);

        List<historyResponse> list = new ArrayList<>();
        for (History record : records) {
            historyResponse historyResponse = new historyResponse();
            historyResponse.setId(record.getId());
            historyResponse.setBname(bookMapper.selectBnameById(record.getBid()));
            historyResponse.setAuthor(bookMapper.selectBookAuthorById(record.getBid()));
            historyResponse.setPress(bookMapper.selectBookPressById(record.getBid()));
            historyResponse.setUser(userMapper.selectUserNameById(record.getUid()));
            historyResponse.setPhone(userMapper.selectUserPhoneById(record.getUid()));
            historyResponse.setWarehouse(warehouseMapper.selectLocationById(record.getWid()));
            historyResponse.setBegin_time(record.getBegin_time());
            historyResponse.setEnd_time(record.getEnd_time());
            historyResponse.setTimes(record.getTimes());
            historyResponse.setStatus(record.getStatus());
            list.add(historyResponse);
        }
        System.out.println(list);

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页,共"+pageSize+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("historyTotal",count);
        data.put("historyList",list);

        return Result.ok(data);
    }


    @Override
    public Result selectHistoryById(Integer id) {
        History history = historyMapper.selectById(id);
        Map data = new LinkedHashMap();
        data.put("tip","成功获取借阅记录");
        data.put("history",history);

        return Result.ok(data);

    }


    @Override
    public Result UpdateHistoryById(Integer id, Integer bid, Integer uid, Integer wid) {
        History history = new History();
        history.setId(id);
        history.setBid(bid);
        history.setUid(uid);
        history.setWid(wid);

        //updateOrderResponse updateOrderResponse = new updateOrderResponse(order);

        Map data = new LinkedHashMap();
        data.put("tip","成功修改订单");
        //data.put("order",updateOrderResponse);

        return Result.ok(data);
    }

    @Override
    public Result createHistory(History history) {

        historyMapper.insert(history);

        Map data = new LinkedHashMap();
        data.put("tip","成功创建借阅记录");
        data.put("history", history);

        return Result.ok(data);
    }

    @Override
    public Result deleteHistoryById(Integer id) {
        History history = historyMapper.selectHistoryById(id);
        historyMapper.deleteHistoryById(id);

        Map data = new LinkedHashMap();
        data.put("tip","成功删除借阅记录");
//        data.put("result",new updateHistoryResponse(history));

        return Result.ok(data);
    }

}




