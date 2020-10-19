package com.mcl.delayq.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_delay_task")
public class DelayTask {

    private Long id;

    /**
     * 业务数据
     */
    private String bizData;

    /**
     * 任务开始执行的时间戳
     */
    private Date exeTime;

    /**
     * 业务处理回调地址
     */
    private String bizCallbackUrl;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}