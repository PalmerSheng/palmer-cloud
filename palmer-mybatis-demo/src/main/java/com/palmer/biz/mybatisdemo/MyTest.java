package com.palmer.biz.mybatisdemo;

import java.time.ZonedDateTime;

/**
 * @author palmer
 * @date 2023-06-21
 */
public class MyTest {
    private static int i = 0;

    public static synchronized void increment() {
        i++;
    }

    public static void main(String[] args) {
        System.out.println(ZonedDateTime.now());
    }
}
