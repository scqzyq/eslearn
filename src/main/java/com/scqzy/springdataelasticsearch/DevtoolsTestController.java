package com.scqzy.springdataelasticsearch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author 盛春强
 * @Date 2021/7/6 16:38
 */
@RestController
public class DevtoolsTestController {

    @GetMapping("devtoolstest")
    public String testDevtools() {
        return "123";
    }
}
