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

import java.time.LocalDateTime;
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
    public Result historyallPageSelect(Integer page, Integer pageSize ) {

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

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页,共"+pageSize+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("historyTotal",count);
        data.put("historyList",list);

        return Result.ok(data);
    }

    public Result historyPageSelect(Integer page, Integer pageSize , Integer uid) {

        if (page == null || page <= 0) {
            page = 1; // 默认第一页
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 15; // 默认每页15条记录
        }

        // 获取订单总数
        Long count = historyMapper.selectHistoryCount();

        //Long count = historyMapper.selectHistoryCountByUid()

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
    public Result UpdateHistoryById(Integer id) {
        History history = new History();

        //updateOrderResponse updateOrderResponse = new updateOrderResponse(order);

        Map data = new LinkedHashMap();
        data.put("tip","成功修改借阅记录");
        //data.put("order",updateOrderResponse);

        return Result.ok(data);
    }

    @Override
    public Result createHistory(History history) {

        Integer num = userMapper.selectNumById(history.getUid());//最大借阅
        Integer count = historyMapper.selectNoHistoryCountByUid(history.getUid());
        Map data = new LinkedHashMap();

        if (num >= count+1) {
            historyMapper.insert(history);

            data.put("tip", "成功创建借阅记录");
            data.put("history", history);

            return Result.ok(data);
        }
        else {
            data.put("tip", "您当前借阅图书太多，借阅已达上限！可以先归还部分书籍哦！");
            data.put("history", history);
            return Result.build(data, 201 , "请求失败");
        }
    }

    @Override
    public Result deleteHistoryById(Integer id) {
        History history = historyMapper.selectHistoryById(id);
        historyMapper.deleteHistoryById(id);

        Map data = new LinkedHashMap();
        data.put("tip","成功删除借阅记录");
        data.put("result",history);

        return Result.ok(data);
    }

    @Override
    public Result continueHistoryById(Integer id) {
        History history = historyMapper.selectHistoryById(id);
        Integer times = userMapper.selectTimesById(history.getUid());//最大续借次数
        Map data = new LinkedHashMap();

        if(times>=history.getTimes()+1){
            historyMapper.UpdateHistoryTimesAndEndTimeById(id);           //续借数+1,end_time+1个月
            history = historyMapper.selectHistoryById(id);

            data.put("tip","成功续借图书");
            data.put("result",history);

            return Result.ok(data);
        }
        else {
            data.put("tip", "您当前图书续借次数太多，借阅已达上限，请先归还！");
            data.put("history", history);
            return Result.build(data, 201 ,"请求失败" );
        }
    }

    @Override
    public Result dealHistoryById(Integer id) {

        LocalDateTime currentTime = LocalDateTime.now();  // 获取当前时间
        historyMapper.UpdateHistoryStatusAndEndTimeById(id,currentTime);           //修改状态0==》1,createtime=>currentTime

        History history = historyMapper.selectHistoryById(id);
        Map data = new LinkedHashMap();
        data.put("tip","成功归还借阅图书");
        data.put("result",history);

        return Result.ok(data);
    }

}




