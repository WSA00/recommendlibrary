package com.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.StockinMapper;
import com.library.mapper.WarehouseMapper;
import com.library.mapper.BookMapper;
import com.library.pojo.Stockin;
import com.library.response.stockinResponse;
import com.library.service.StockinService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* 针对表【stockin】的数据库操作Service实现
*/
@Service
public class StockinServiceImpl extends ServiceImpl<StockinMapper, Stockin>
    implements StockinService{

    @Autowired
    private StockinMapper stockinMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public Result createStockin(Stockin stockin) {
        stockinMapper.createStockin(stockin);

        Map data = new LinkedHashMap();
        data.put("tip","成功创建供应记录");
        data.put("stockin",stockin);

        return Result.ok(data);
    }

    @Override
    public Result stockinPageSelect(Integer page, Integer pageSize) {

        Long count = stockinMapper.selectCount(null);

        List<Stockin> records = stockinMapper.selectStockinPage((page - 1) * pageSize, pageSize);

        List list = new ArrayList<>();
        for (Stockin record : records) {
            stockinResponse stockinResponse = new stockinResponse();
            stockinResponse.setId(record.getId());
            stockinResponse.setBname(bookMapper.selectBnameById(record.getBid()));
            stockinResponse.setAuthor(bookMapper.selectBookAuthorById(record.getBid()));
            stockinResponse.setPress(bookMapper.selectBookPressById(record.getBid()));
            stockinResponse.setQuantity(record.getQuantity());
            stockinResponse.setWarehouse(warehouseMapper.selectLocationById(record.getWid()));
            stockinResponse.setCreatetime(String.valueOf(record.getCreatetime()));

            list.add(stockinResponse);
        }

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页,共"+pageSize+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("stockinTotal",count);
        data.put("stockinList",list);

        return Result.ok(data);
    }

    @Override
    public Result deleteStockinById(Integer id) {

        stockinMapper.deleteById(id);

        Map data = new LinkedHashMap();
        data.put("tip","成功删除入库记录");

        return Result.ok(data);
    }


}




