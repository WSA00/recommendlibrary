package com.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.pojo.History;
import com.library.utils.Result;

import java.util.Date;

/*
*针对表【history】的数据库操作Service
*/
public interface HistoryService extends IService<History> {
    Result historyallPageSelect(Integer page, Integer pageSize);

    Result historyPageSelect(Integer page, Integer pageSize , Integer uid);

    Result selectHistoryById(Integer id);

    Result createHistory(History history);

    Result deleteHistoryById(Integer id);

    Result continueHistoryById(Integer id);

    Result dealHistoryById(Integer id);

}
