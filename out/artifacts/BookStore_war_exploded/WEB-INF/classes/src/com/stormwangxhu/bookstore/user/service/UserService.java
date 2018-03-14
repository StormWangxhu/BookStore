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

    /**
     * 激活功能
     * @param code
     */
    public void active(String code) throws UserException{
        //1、使用code查讯数据库，得到User对象
        User user =userDao.findByCode(code);
        //2、若user不存在，说明激活码无效
        if (user==null) throw new RuntimeException("激活码无效！");
        //3、检验用户状态是否为已激活状态，若为已经激活，则抛出异常
        if (user.isState()) throw new RuntimeException("您已经激活！");
        //4、修改用户的状态
        userDao.updateState(user.getUid(),true);
    }

    /**
     * 用户登录
     * @param form
     * @return
     * @throws UserException
     */
    public User login(User form) throws UserException{
        String username =form.getUsername();
        User user =userDao.findByUsername(username);
        if (user==null) throw new RuntimeException("用户名不存在！");
        if (!form.getPassword().equals(user.getPassword()))
            throw new RuntimeException("密码错误！");
        //判断用户状态
        if (!user.isState()) throw new RuntimeException("用户尚未激活！");
        return user;
    }
}
