package com.mcl.delayq.job;

import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcl.delayq.common.util.PageUtils;
import com.mcl.delayq.common.util.SelfCancelRunnable;
import com.mcl.delayq.common.util.ThreadPooler;
import com.mcl.delayq.entity.DelayTask;
import com.mcl.delayq.service.DelayTaskService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @auth caiguowei
 * @date 2020/10/19
 */
@Component
public class LoopJob implements InitializingBean {

    private static final Long interval = 5L; // 循环间隔(单位:min)

    // 秒
    @Value("${biz.loop.interval:10}")
    private Integer loopInterval;

    // 分钟
    @Value("${biz.task.step:10}")
    private Integer taskStep;


    @Autowired
    private DelayTaskService delayTaskService;

    @Override
    public void afterPropertiesSet() throws Exception {
        ThreadPooler.scheduleAsyncRatedRunnableOnDaemon(new SelfCancelRunnable() {
            @Override
            public void run() {
                IPage page = new Page(1,10);
                do{
                    page = handle(page);
                } while (page.getCurrent() < page.getPages());
            }
        },0,loopInterval, TimeUnit.SECONDS);
    }

    private IPage handle(IPage page){
        IPage<DelayTask> delayTaskPage = delayTaskService.selectByPageWithinMinutes(page,taskStep);
        if (PageUtils.isEmpty(delayTaskPage)){
            return new Page(1,10);
        }
        long timestamp = new Date().getTime();
        delayTaskPage.getRecords().forEach(delayTask -> {
            ThreadPooler.scheduleAsyncRunnableOnDaemon(()->{
                try {
                    String body = HttpRequest.post(delayTask.getBizCallbackUrl())
                            .timeout(1000)
                            .body(delayTask.getBizData()).contentType("application/json;charset=UTF-8").execute().body();
                    if ("SUCCESS".equals(body)){
                        delayTaskService.deleteByIds(Collections.singletonList(delayTask.getId()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // 忽略异常
                }
            },delayTask.getExeTime().getTime()-timestamp,TimeUnit.MILLISECONDS);
        });
        return delayTaskPage;
    }
}
