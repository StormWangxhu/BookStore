package com.stormwangxhu.bookstore.user.domain;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class UserException extends Exception {
    public UserException(String message) {
        super(message);
    }

    public UserException() {
        super();
    }
}
