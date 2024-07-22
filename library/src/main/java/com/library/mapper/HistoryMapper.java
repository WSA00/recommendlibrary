package com.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.pojo.History;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HistoryMapper extends BaseMapper<History> {

    Long selectHistoryCount();

    List<History> selectHistoryPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    List<History> selectHistoryPageByUid(@Param("uid")Integer uid,@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    History selectHistoryById(@Param("hid") Integer hid);

    void deleteHistoryById(@Param("id") Integer id);

    Integer selectIncome();

    Integer orders();

    Integer warehouses();

    Integer users();

    List<Integer> selectTopUserId();

    List<String> selectTopUserName();

    Integer selectOrdersByYearAndUserId(@Param("year") Integer year, @Param("id") Integer id);

//
    List<Integer> selectTopBookId();

    List<String> selectTopBookName();

    List<String> selectTopBookAuthor();

    List<String> selectTopBookPress();

    Integer selectOrdersByYearMonthAndBookId(Integer j, @Param("id") Integer id);
//

    List<History> selectHistoryByUserId( Integer uid);

    void UpdateHistoryTimesAndEndTimeById(Integer hid);//续借

    void UpdateHistoryStatusAndEndTimeById(Integer hid);//归还

    Integer selectHistoryCountByUid(Integer uid);

    Integer selectNoHistoryCountByUid(Integer uid);

    Integer selectMonthCountById(Integer id);

}
