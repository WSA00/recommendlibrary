package com.library.response;

import lombok.Data;

@Data
public class stockinResponse {
    private Integer id;
    private String bname;
    private String author;
    private String press;
    private String warehouse;
    private Integer quantity;
    private String createtime;
}
