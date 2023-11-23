package com.palmer.module.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author palmer
 * @date 2023-11-22
 */
@RestController
@RequestMapping("testa")
public class TestController2 {
    @GetMapping("testa")
    public String test() {
        return "test";
    }
}
