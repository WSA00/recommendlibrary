package com.library.response;

import com.library.pojo.Book;
import lombok.Data;

@Data
public class BookResponse {
    private Integer id;

    private String bname;

    private Integer tid;

    private String author;

    private String press;

    private Integer btimes;

    private String introduce;

    private String tname;

    private String poster;

    public BookResponse() {
    }

    public BookResponse(Book book){
        this.id=book.getId();
        this.bname=book.getBname();
        this.tid=book.getTid();
        this.author=book.getAuthor();
        this.press= book.getPress();
        this.btimes= book.getBtimes();
        this.introduce= book.getIntroduce();
        this.poster=book.getPoster();
    }
}
