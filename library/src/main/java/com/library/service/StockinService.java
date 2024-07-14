package com.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.library.pojo.Supply;
import com.library.pojo.Stockin;
import com.library.utils.Result;

/*
*针对表【stockin】的数据库操作Service
*/
public interface StockinService extends IService<Stockin> {

    Result createStockin(Stockin stockin);

    Result stockinPageSelect(Integer page, Integer pageSize);

    Result deleteStockinById(Integer id);
}
