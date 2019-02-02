package com.yoga.order.controller;

import com.yoga.order.constant.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yj
 * @date 2019/1/30 11:57
 */
@RestController
@Slf4j
public class TestController {
    @GetMapping("/test")
    public BaseResponse print(@RequestParam("userName") String userName, @RequestParam("password") String password){
        log.info("print userName={},password={}",userName,password);
        return BaseResponse.successInstance("userName="+userName+"password="+password+"登录成功");
    }
}
