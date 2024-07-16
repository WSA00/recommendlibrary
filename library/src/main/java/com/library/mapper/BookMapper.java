package com.library.mapper;

import com.library.pojo.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.response.ProductNameAndValue;
import com.library.response.ProductYearAndSalesResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BookMapper extends BaseMapper<Book> {
    List<Book> getAllBook();

    List<Book> selectBookByTid(Integer tid);

    Integer selectBookCountByTid(Integer tid);

    String selectBnameById(@Param("bid") Integer bid);

    String selectBookAuthorById(@Param("bid") Integer bid);

    String selectBookPressById(@Param("bid") Integer bid);

//    Integer saleOfCarById(Integer id);

//    Book selectBookById(@Param("bid") Integer bid);

//    List<ProductNameAndValue> selectProductNameAndValueById(@Param("bid") Integer bid);

//
//    List<ProductYearAndSalesResponse> selectYearAndSalesById(Integer id);
}
