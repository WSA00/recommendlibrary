package com.library.service.impl;

import com.library.mapper.HistoryMapper;
import com.library.response.historyResponse;
import com.library.service.ChartService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.time.Year;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public Result income() {
        Integer income = historyMapper.selectIncome();

        Map data = new LinkedHashMap();
        data.put("tip","成功获取营业额");
        data.put("total",income);

        return Result.ok(data);
    }

    @Override
    public Result orders() {
        Integer orders = historyMapper.orders();

        Map data = new LinkedHashMap();
        data.put("tip","成功获取借阅量");
        data.put("total",orders);

        return Result.ok(data);
    }

    @Override
    public Result warehouses() {
        Integer warehouse = historyMapper.warehouses();

        Map data = new LinkedHashMap();
        data.put("tip","成功获取仓库数");
        data.put("total",warehouse);

        return Result.ok(data);
    }

    @Override
    public Result users() {
        Integer users = historyMapper.users();

        Map data = new LinkedHashMap();
        data.put("tip","成功获取用户数");
        data.put("total",users);

        return Result.ok(data);
    }

    @Override
    public Result user() {
        List<Integer> userId = historyMapper.selectTopUserId();
        List<String> userName = historyMapper.selectTopUserName();

        int currentYear = Year.now().getValue();
        List<Integer> years = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {
            years.add(currentYear - i);
        }

        List<List<Integer>> source = new ArrayList<>();
        for (Integer id : userId) {
            List<Integer> ordersByYear = new ArrayList<>();
            for (Integer year : years) {
                Integer orders = historyMapper.selectOrdersByYearAndUserId(year, id);
                ordersByYear.add(orders != null ? orders : 0); // 如果借阅量为null，则默认为0
            }
            source.add(ordersByYear);
        }

        Map data = new LinkedHashMap();
        data.put("tip","成功获取用户借阅量排行榜");
        data.put("idList",userId);
        data.put("xList",userName);
        data.put("yList",years);
        data.put("source", source);
        return Result.ok(data);
    }

    @Override
    public Result book() {
        List<Integer> bookId = historyMapper.selectTopBookId();
        List<String> bookName = historyMapper.selectTopBookName();
        List<String> bookAuthor = historyMapper.selectTopBookAuthor();
        List<String> bookPress = historyMapper.selectTopBookPress();

        List<String> fullBookList = new ArrayList<>();
        for (int i = 0; i < bookName.size(); i++) {
            fullBookList.add(bookName.get(i) + "-" + bookAuthor.get(i)+ "-" + bookPress.get(i));
        }

        // 获取当前日期的年份和月份
        LocalDate currentDate = LocalDate.now();

        List<String> yearMonths = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            // 计算当前日期前第i个月的年份和月份
            LocalDate date = currentDate.minusMonths(i);
            int year = date.getYear();
            int month = date.getMonthValue();
            // 格式化成"yyyy-MM"的字符串
            String yearMonth = String.format("%d-%02d", year, month);
            yearMonths.add(yearMonth);
        }

        List<List<Object>> source = new ArrayList<>();
        List<Object> header = new ArrayList<>();
        header.add("orders"); // 添加借阅量列
        header.add("book"); // 添加书本名称列
        header.add("yearMonth"); // 添加年份列
        source.add(header); // 添加表头

       //  获取每个月份每个图书的借阅量数据，并加入source
        for (int j = 0; j < yearMonths.size(); j++) {
            for (int i = 0; i < bookId.size(); i++) {
                // 根据产品ID和当年年份查询借阅数据
                int orders = historyMapper.selectOrdersByYearMonthAndBookId( j,bookId.get(i));
                // 创建包含销量、产品名称和年份的子列表
                List<Object> orderData = new ArrayList<>();
                orderData.add(orders);
                orderData.add(bookName.get(i) + "-" + bookAuthor.get(i)+ "-" + bookPress.get(i));
                orderData.add(yearMonths.get(j));
                // 将销量数据加入source
                source.add(orderData);
            }
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "成功获取热门图书榜");
        data.put("idList", bookId);
        data.put("xList", fullBookList);
        data.put("yList", yearMonths);
        data.put("source", source);
        return Result.ok(data);
    }

    @Override
    public Result warn(Integer id) {

        List <historyResponse> warn = historyMapper.warn(id);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "温馨提示");
        data.put("warn", warn);
        System.out.println(data);
        return Result.ok(data);
    }

}
