package com.library.pojo;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName order
 */
@Data
@TableName("Order")
public class Order implements Serializable {

    @TableId
    private Integer id;

    private Integer productId;

    private Integer userId;

    private Integer warehouseId;

    private Date createtime;

    private static final long serialVersionUID = 1L;
}