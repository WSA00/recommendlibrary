package com.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.pojo.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeMapper extends BaseMapper<Type> {

    Integer selectIdByTname(@Param("tname") String tname);

    List<Type> getAllType();

    String selectTnameById(@Param("tid") Integer tid);
}
