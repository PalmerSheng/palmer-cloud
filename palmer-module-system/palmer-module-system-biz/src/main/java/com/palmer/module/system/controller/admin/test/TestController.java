package com.palmer.module.system.controller.admin.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author palmer
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/system/test")
public class TestController {

    @PreAuthorize("@ss.hasAnyRoles('super_admin111')")
    @GetMapping("/test1")
    public String test() {
        return "test";
    }
}
