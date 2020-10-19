package com.mcl.delayq.controller.req;

import lombok.Data;
import java.util.Date;

/**
 * @auth caiguowei
 * @date 2020/10/19
 */
@Data
public class CommitDelayTaskReq {

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

}
