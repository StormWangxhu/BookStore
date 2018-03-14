package com.stormwangxhu.bookstore.user.dao;

import cn.itcast.jdbc.TxQueryRunner;
import com.stormwangxhu.bookstore.user.domain.User;
import com.stormwangxhu.bookstore.user.domain.UserException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @Description: User持久层
 * @Author StormWangxhu
 * @Date Created in 2018/3/11
 */
public class UserDao {
    QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 按用户名查找
     *
     * @param username
     * @return
     */
    public User findByUsername(String username) throws UserException{
        String sql = "select * from user where username =?";
        try {
            return queryRunner.query(sql, new BeanHandler<User>(User.class), username);
        } catch (SQLException e) {
            throw new RuntimeException("查询失败！");
        }
    }

    /**
     * 按邮箱查找
     *
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        String sql = "select * from user where email =?";
        try {
            return queryRunner.query(sql, new BeanHandler<User>(User.class), email);
        } catch (SQLException e) {
            throw new RuntimeException("查询失败！");
        }
    }

    /**
     * 完成添加用户
     *
     * @param user
     */
    public void addUser(User user) {
        String sql = "insert into user values(?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.getCode(), user.isState()};
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException("添加用户失败！");
        }
    }

    /**
     * 按激活码查询
     * @param code
     * @return
     */
    public User findByCode(String code) {
        String sql = "select * from user where code =?";
        try {
            return queryRunner.query(sql, new BeanHandler<User>(User.class), code);
        } catch (SQLException e) {
            throw new RuntimeException("查询失败！");
        }
    }

    /**
     * 修改指定用户的指定状态
     * @param uid
     * @param state
     */
    public void updateState(String uid,boolean state) {
        String sql = "update user set state=? where uid=?";
        try {
            queryRunner.update(sql, state,uid);
        } catch (SQLException e) {
            throw new RuntimeException("添加用户失败！");
        }
    }

}
