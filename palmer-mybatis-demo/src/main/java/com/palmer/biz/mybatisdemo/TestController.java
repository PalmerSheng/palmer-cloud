package com.palmer.biz.mybatisdemo;

import com.palmer.framework.common.exception.enums.GlobalErrorCodeConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author palmer
 * @date 2023-06-19
 */
@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping("test1")
    public String test() {
        throw new RuntimeException(GlobalErrorCodeConstants.UNKNOWN.getMsg());
    }
}
