package com.library.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.library.response.BookResponse;
import lombok.Data;

@Data
@TableName("book")
public class Book {

    @TableId
    private Integer id;

    private String bname;

    private Integer tid;

    private String author;

    private String press;

    private Integer btimes;

    private String introduce;

    private String poster;

    private static final long serialVersionUID = 1L;

    public Book() {
    }

    public Book(BookResponse bookResponse)
    {
        this.id=bookResponse.getId();
        this.bname=bookResponse.getBname();
        this.tid=bookResponse.getTid();
        this.author=bookResponse.getAuthor();
        this.press= bookResponse.getPress();
        this.btimes= bookResponse.getBtimes();
        this.introduce= bookResponse.getIntroduce();
        this.poster=bookResponse.getPoster();
    }
}


