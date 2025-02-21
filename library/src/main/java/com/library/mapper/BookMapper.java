package com.library.mapper;

import com.library.pojo.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.response.BookNameAndValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BookMapper extends BaseMapper<Book> {
    List<Book> getAllBook();

    List<Book> selectBookByTid(Integer tid);

    Integer selectBookCountByTid(Integer tid);

    String selectBnameById(@Param("bid") Integer bid);

    String selectBookAuthorById(@Param("bid") Integer bid);

    String selectBookPressById(@Param("bid") Integer bid);

    Integer selectBookCountByBnameOrAuthor(String BnameOrAuthor);

    List<BookNameAndValue> selectBookNameAndValueById(@Param("bid") Integer bid);

    List<Book> getRandomBook();
}
