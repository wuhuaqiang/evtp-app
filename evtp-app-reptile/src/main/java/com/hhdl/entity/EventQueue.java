package com.hhdl.entity;


public interface EventQueue<T> {

    void offer(T event) throws InterruptedException;

    T take() throws InterruptedException;


}
