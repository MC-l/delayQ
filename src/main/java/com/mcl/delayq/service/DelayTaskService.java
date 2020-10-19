package com.mcl.delayq.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mcl.delayq.common.util.ObjUtils;
import com.mcl.delayq.controller.req.CommitDelayTaskReq;
import com.mcl.delayq.entity.DelayTask;
import com.mcl.delayq.mapper.DelayTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @auth caiguowei
 * @date 2020/10/19
 */
@Component
public class DelayTaskService {

    @Autowired
    private DelayTaskMapper delayTaskMapper;

    /**
     * 提交延时任务
     * @param req
     */
    public void commit(CommitDelayTaskReq req) {
        delayTaskMapper.insert(ObjUtils.copy(req, DelayTask.class));
    }

    public IPage<DelayTask> selectByPageWithinMinutes(IPage page, int minutes) {
        DateTime dateTime = DateUtil.offsetSecond(new Date(), minutes * 60);
        return delayTaskMapper.selectPage(page, Wrappers.<DelayTask>lambdaQuery().lt(DelayTask::getExeTime,dateTime));
    }

    public void deleteByIds(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)){
            delayTaskMapper.deleteBatchIds(ids);
        }
    }
}
