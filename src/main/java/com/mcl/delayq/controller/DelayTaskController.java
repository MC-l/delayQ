package com.mcl.delayq.controller;

import com.mcl.delayq.controller.req.CommitDelayTaskReq;
import com.mcl.delayq.service.DelayTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth caiguowei
 * @date 2020/10/19
 */
@RestController
@RequestMapping("delayTask")
public class DelayTaskController {

    @Autowired
    private DelayTaskService delayTaskService;

    @PostMapping("commit")
    public void commit(@RequestBody CommitDelayTaskReq req){
        delayTaskService.commit(req);
    }
}
