package com.library.response;

import lombok.Data;

import java.util.Date;

@Data
public class historyResponse {
    private Integer id;
    private String bname;
    private String author;
    private String press;
    private String user;
    private String phone;
    private String warehouse;
//    private Date createtime;
    private Date begin_time;
    private Date end_time;

}

