package com.palmer.biz.mybatisdemo.test;

/**
 * @author palmer
 * @date 2023-08-03
 */
public interface DefaultInterface {
    default  void print() {
        System.out.println("default");

    }
}
