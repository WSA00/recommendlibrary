package com.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.pojo.History;
import com.library.response.historyResponse;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryMapper extends BaseMapper<History> {

    Long selectHistoryCount();

    List<History> selectHistoryPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    List<History> selectHistoryPageByUid(@Param("uid")Integer uid,@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    History selectHistoryById(@Param("hid") Integer hid);

    void deleteHistoryById(@Param("id") Integer id);

    Double selectIncome();

    Integer sales();

    Integer warehouses();

    Integer users();

    List<Integer> selectTopUserId();

    List<String> selectTopUserName();

    Double selectSalesByYearAndUserId(@Param("year") Integer year, @Param("id") Integer id);

    List<Integer> selectTopCarId();

    List<String> selectTopCarName();

    List<String> selectTopCarModel();

    Integer selectSalesByYearAndCarId(@Param("year") Integer year, @Param("id") Integer id);

    List<historyResponse> selectHistoryByUserId(@Param("uid") String uid);

    void UpdateHistoryTimesAndEndTimeById(Integer hid);//续借

    void UpdateHistoryStatusAndEndTimeById(Integer hid, LocalDateTime currentTime);//归还

    Integer selectHistoryCountByUid(Integer uid);

    Integer selectNoHistoryCountByUid(Integer uid);

}
