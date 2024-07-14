package com.library.service;

import com.library.pojo.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.library.response.BookResponse;
import com.library.utils.Result;

/**
 * book
 */
public interface BookService extends IService<Book> {

    Result createBook(BookResponse bookResponse);

    Result bookPageSelect(Integer page, Integer pageSize);

    Result bookPageSelect1(Integer page, Integer pageSize , String tid);

    Result selectBookById(Integer id);

    Result UpdateBookById(Integer id, String bname, String tname, String author, String press,String introduce,String poster);

    Result deleteBookById(Integer id);

    Result selectWarehouseByBookId(Integer id);

    Result getAllBook();

    Result selectBookByTid(Integer tid);
}
