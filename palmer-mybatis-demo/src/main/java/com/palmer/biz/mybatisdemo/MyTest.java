package com.palmer.biz.mybatisdemo;

/**
 * @author palmer
 * @date 2023-06-21
 */
public class MyTest {
    private static int i = 0;

    public  static  synchronized  void increment() {
        i++;
    }
}
