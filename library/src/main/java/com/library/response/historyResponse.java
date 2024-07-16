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

    private Integer times;//续借数

    private Integer status;//订单状态，0为正在借，1为已归还

}

