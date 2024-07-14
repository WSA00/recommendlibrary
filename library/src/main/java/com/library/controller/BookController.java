package com.library.controller;

import com.library.pojo.Book;
import com.library.response.BookResponse;
import com.library.service.BookService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @PostMapping("")
    public Result createBook(@RequestBody BookResponse book){
        return bookService.createBook(book);
    }

    @PreAuthorize("hasAnyAuthority('USER','ROOT')")
    @GetMapping("")
    public Result bookPageSelect(Integer page, Integer pageSize){

        if (page == null && pageSize == null) {
                return bookService.getAllBook();
        }else {
                return bookService.bookPageSelect(page,pageSize);
        }
    }

    @PreAuthorize("hasAnyAuthority('USER','ROOT')")
    @GetMapping("/type")
    public Result bookPageSelect1(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String tid){
                return bookService.bookPageSelect1(page,pageSize,tid);
    }

    @PreAuthorize("hasAnyAuthority('USER','ROOT')")
    @GetMapping("{id}")
    public Result selectBookById(@PathVariable Integer id){
        return bookService.selectBookById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @PatchMapping("{id}")
    public Result UpdateBookById(@PathVariable Integer id, @RequestBody  String bname, String tname, String author, String press,String introduce,String poster){
        return bookService.UpdateBookById(id,bname,tname,author, press, introduce,poster);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @DeleteMapping("{id}")
    public Result deleteBookById(@PathVariable Integer id){
        return bookService.deleteBookById(id);
    }

    @PreAuthorize("hasAnyAuthority('USER','ROOT')")
    @GetMapping("{id}/inventory")
    public Result selectWarehouseByBookId(@PathVariable Integer id){
        return bookService.selectWarehouseByBookId(id);
    }

}
