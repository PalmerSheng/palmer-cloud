package com.palmer.biz.mybatisdemo.entity;

import lombok.Data;

/**
 * @author palmer
 * @date 2023-07-07
 */
@Data
public class MyConfig2 implements MyConfigInterface {
    private String secret = "sf";
}
