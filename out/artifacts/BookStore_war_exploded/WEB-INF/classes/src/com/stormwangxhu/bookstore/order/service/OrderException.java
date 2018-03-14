package com.stormwangxhu.bookstore.order.service;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/14
 */
public class OrderException extends Exception {
    public OrderException(String message) {
        super(message);
    }

    public OrderException() {
        super();
    }
}
