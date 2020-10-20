package com.mcl.delayq.test;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth caiguowei
 * @date 2020/10/20
 */
@RestController
@RequestMapping("test/callback")
public class TestCallbackController {

    @PostMapping("test")
    public String test(@RequestBody String bizData){
        System.out.println("业务后台被调用,参数 = "+bizData);
        return "SUCCESS";
    }
}
