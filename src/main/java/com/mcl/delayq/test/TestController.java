package com.mcl.delayq.test;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.mcl.delayq.common.util.JsonUtils;
import com.mcl.delayq.controller.req.CommitDelayTaskReq;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @auth caiguowei
 * @date 2020/10/20
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("commit")
    public void commit(){
        CommitDelayTaskReq task = new CommitDelayTaskReq();
        task.setBizCallbackUrl("http://localhost:21148/test/callback");
        task.setBizData("Hello");
        task.setExeTime(new Date());
        HttpResponse execute = HttpRequest.post("http://localhost:21148/delayTask/commit")
                .body(JSONUtil.toJsonStr(task))
                .contentType("application/json;charset=UTF-8").execute();
        System.out.println(execute.getStatus());
        System.out.println(execute.body());

    }

    @PostMapping("callback")
    public String callback(@RequestBody String bizData){
        System.out.println("业务后台被调用,参数 = "+bizData);
        return "SUCCESS";
    }
}
