package com.library.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("type")
public class Type {

    @TableId
    private Integer id;

    private String tname;

    private static final long serialVersionUID = 1L;
}
