package com.stormwangxhu.bookstore.user.service;

import com.stormwangxhu.bookstore.user.dao.UserDao;
import com.stormwangxhu.bookstore.user.domain.User;
import com.stormwangxhu.bookstore.user.domain.UserException;

/**
 * @Description: User业务层
 * @Author StormWangxhu
 * @Date Created in 2018/3/11
 */
public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 注册功能
     * @param form
     * @throws UserException
     */
    public void regist(User form) throws UserException{
        //校验用户名
        User user = userDao.findByUsername(form.getUsername());
        if (user!=null) throw new UserException("用户已经被注册！");

        //校验邮箱
        user = userDao.findByUsername(form.getEmail());
        if (user!=null) throw new UserException("用户已经被注册！");

        //插入用户
        userDao.addUser(form);
    }
}
