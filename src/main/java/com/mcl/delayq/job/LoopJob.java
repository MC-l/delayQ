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

    private Long interval = 5L; // 循环间隔

    @Autowired
    private DelayTaskService delayTaskService;

    @Override
    public void afterPropertiesSet() throws Exception {
        ThreadPooler.scheduleAsyncRatedRunnableOnDaemon(new SelfCancelRunnable() {
            @Override
            public void run() {
                IPage page = new Page(1,10);
                do{
                    page = recursionHandle(page);
                } while (page.getCurrent() < page.getPages());
            }
        },0,interval, TimeUnit.SECONDS);
    }

    private IPage recursionHandle(IPage page){
        IPage<DelayTask> delayTaskPage = delayTaskService.selectByPageWithinMinutes(page,5);
        if (PageUtils.isEmpty(delayTaskPage)){
            return new Page(1,10);
        }
        long timestamp = new Date().getTime();
        delayTaskPage.getRecords().forEach(delayTask -> {
            ThreadPooler.scheduleAsyncRunnableOnDaemon(()->{
                System.out.println("任务期望时间:"+delayTask.getExeTime().getTime()+"   任务执行时间:"+timestamp);
                try {
//                    String body = HttpRequest.post(delayTask.getBizCallbackUrl())
//                            .timeout(10)
//                            .body(delayTask.getBizData()).contentType("application/json;charset=UTF-8").execute().body();
//                    System.out.println("返回:"+body);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 忽略异常
                }
                delayTaskService.deleteByIds(Collections.singletonList(delayTask.getId()));
            },delayTask.getExeTime().getTime()-timestamp,TimeUnit.MILLISECONDS);
        });
        return delayTaskPage;
    }
}
