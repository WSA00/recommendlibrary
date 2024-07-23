package com.library.response;

import lombok.Data;

@Data
public class inventoryResponse {
    private Integer id;
    private String bname;
    private String author;
    private String press;
    private Integer quantity;
}
