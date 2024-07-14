package com.library.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("history")
public class History {

    @TableId
    private Integer id;

    private Integer uid;

    private Integer bid;

    private Integer wid;

    private Date begin_time;

    private Date end_time;

    private static final long serialVersionUID = 1L;

}
