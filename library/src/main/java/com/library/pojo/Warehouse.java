package com.library.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName warehouse
 */
@Data
@TableName("warehouse")
public class Warehouse implements Serializable {

    @TableId
    private Integer id;

    private String location;

    private static final long serialVersionUID = 1L;
}