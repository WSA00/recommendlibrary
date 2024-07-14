package com.library.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.TypeMapper;
import com.library.pojo.Type;
import com.library.service.TypeService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//针对表【type】的数据库操作Service

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
        implements TypeService{

    @Autowired
    private TypeMapper typeMapper;


    @Override
    public Result createType(Type type) {
        typeMapper.insert(type);

        Map data = new LinkedHashMap();
        data.put("tip","成功创建图书类型");
        data.put("type",type);

        return Result.ok(data);
    }

    @Override
    public Result TypePageSelect(Integer page, Integer pageSize) {
        Page<Map<String, Object>> mapPage = new Page<>(page, pageSize);

        IPage<Map<String, Object>> result = typeMapper.selectMapsPage(mapPage,null);
        List<Map<String, Object>> records = result.getRecords();

        Long count = typeMapper.selectCount(null);

        List list = new ArrayList<>();
        for (Map<String, Object> record : records) {
            Type type = new Type();
            type.setId((Integer) record.get("id"));
            type.setTname((String) record.get("tname"));

            list.add(type);
        }

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页,共"+pageSize+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("typeTotal",count);
        data.put("typeList",list);

        return Result.ok(data);
    }

    @Override
    public Result selectTypeById(Integer id) {
        Type type = typeMapper.selectById(id);

        Map data = new LinkedHashMap();
        data.put("tip","成功获取指定图书类型");
        data.put("type",type);

        return Result.ok(data);
    }

    @Override
    public Result UpdateTypeById(Integer id, Type type) {

        type.setId(id);
        typeMapper.updateById(type);

        Map data = new LinkedHashMap();
        data.put("tip","成功修改图书类型");
        data.put("type",type);

        return Result.ok(data);
    }

    @Override
    public Result deleteTypeById(Integer id) {

        typeMapper.deleteById(id);

        Map data = new LinkedHashMap();
        data.put("tip","成功删除图书类型");

        return Result.ok(data);
    }

    @Override
    public Result getAllType() {
        List<Type> types = typeMapper.getAllType();
        return Result.ok(types);
    }
}