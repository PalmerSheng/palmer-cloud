package com.palmer.biz.mybatisdemo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author palmer
 * @date 2023-06-19
 */
@RestController
@Api(tags = {"a", "b"})
public class TestController {
    @GetMapping("test1")
    @ApiOperation(value = "test1")
    public String test1() {
        return "test1";
    }

    @GetMapping("test2")
    @ApiOperation(value = "test2")
    public String test2() {
        return "test2";
    }


    @GetMapping("test3")
    @ApiOperation(value = "test3")
    public String test3() {
        return "test3";
    }
}
