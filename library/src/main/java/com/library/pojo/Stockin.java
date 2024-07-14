package com.library.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("stockin")
public class Stockin {

    @TableId
    private Integer id;

    private Integer bid;

    private Integer wid;

    private Integer quantity;

    private Date createtime;

    private static final long serialVersionUID = 1L;
}
