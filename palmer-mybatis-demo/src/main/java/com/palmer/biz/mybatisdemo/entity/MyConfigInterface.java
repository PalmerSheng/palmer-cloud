package com.palmer.biz.mybatisdemo.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author palmer
 * @date 2023-07-07
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface MyConfigInterface {
}
