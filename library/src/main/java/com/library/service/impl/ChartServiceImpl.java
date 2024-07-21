package com.library.service.impl;

import com.library.mapper.HistoryMapper;
import com.library.service.ChartService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Double income = historyMapper.selectIncome();

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
        for (int i = 4; i >= 0; i--) {
            years.add(currentYear - i);
        }

        List<List<Double>> source = new ArrayList<>();
        for (Integer id : userId) {
            List<Double> salesByYear = new ArrayList<>();
            for (Integer year : years) {
                Double sales = historyMapper.selectSalesByYearAndUserId(year, id);
                salesByYear.add(sales != null ? sales : 0); // 如果借阅量为null，则默认为0
            }
            source.add(salesByYear);
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
        List<Integer> carId = historyMapper.selectTopCarId();
        List<String> carName = historyMapper.selectTopCarName();
        List<String> carModel = historyMapper.selectTopCarModel();

        List<String> fullCarList = new ArrayList<>();
        for (int i = 0; i < carName.size(); i++) {
            fullCarList.add(carName.get(i) + "-" + carModel.get(i));
        }

        int currentYear = Year.now().getValue();
        List<Integer> years = new ArrayList<>();
        for (int i = 9; i >= 0; i--) {
            years.add(currentYear - i);
        }

        List<List<Object>> source = new ArrayList<>();
        List<Object> header = new ArrayList<>();
        header.add("orders"); // 添加销售额列
        header.add("book"); // 添加汽车名称列
        header.add("year"); // 添加年份列
        source.add(header); // 添加表头selectSalesByYearAndCarId

        // 获取每个年份每个产品的销量数据，并加入source
        for (int j = 0; j < years.size(); j++) {
            for (int i = 0; i < carId.size(); i++) {
                // 根据产品ID和年份查询销量数据，这里需要根据实际情况调整方法名和参数
                int sales = historyMapper.selectSalesByYearAndCarId( years.get(j),carId.get(i));
                // 创建包含销量、产品名称和年份的子列表
                List<Object> saleData = new ArrayList<>();
                saleData.add(sales);
                saleData.add(carName.get(i)+"-"+carModel.get(i));
                saleData.add(years.get(j));
                // 将销量数据加入source
                source.add(saleData);
            }
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "成功获取热门图书榜");
        data.put("idList", carId);
        data.put("xList", fullCarList);
        data.put("yList", years);
        data.put("source", source);

        return Result.ok(data);
    }

}
