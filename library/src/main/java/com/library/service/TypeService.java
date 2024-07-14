package com.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.pojo.Type;
import com.library.utils.Result;

//针对表【type】的数据库操作Service

    public interface TypeService extends IService<Type> {

        Result createType(Type type);

        Result TypePageSelect(Integer page, Integer pageSize);

        Result selectTypeById(Integer id);

        Result UpdateTypeById(Integer id, Type type);

        Result deleteTypeById(Integer id);

        Result getAllType();

    }
