package com.library.response;

import lombok.Data;

@Data
public class UserHistoryResponse {
    private String id;
    private String name;
    private String model;
    private String location;
    private String createtime;
}
