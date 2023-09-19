package com.palmer.biz.mybatisdemo.test;

/**
 * @author palmer
 * @date 2023-08-03
 */
public class DefaultInterfaceImpl implements DefaultInterface {
    @Override
    public void print() {
        DefaultInterface.super.print();
    }
}
