package com.stormwangxhu.bookstore.category.dao;

import cn.itcast.jdbc.TxQueryRunner;
import com.stormwangxhu.bookstore.category.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class CategoryDao {
    QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 查询所有图书
     *
     * @return
     */
    public List<Category> findAll() {
        String sql = "select * from category";
        try {
            return queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
        } catch (SQLException e) {
            throw new RuntimeException("查询所有分类失败！");
        }
    }

    /**
     * 后台管理之添加分类
     * @param category
     */
    public void add(Category category) {
        String sql ="insert into category values(?,?)";
        Object[] params={category.getCid(),category.getCname()};
        try {
            queryRunner.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 后台管理之删除分类
     * @param cid
     */
    public void delete(String cid) {
        String sql="delete from category where cid=?";
        try {
            queryRunner.update(sql,cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载分类
     * @param cid
     * @return
     */
    public Category load(String cid) {
        String sql="select * from category where cid=?";
        try {
            return queryRunner.query(sql,new BeanHandler<Category>(Category.class),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改分类
     * @param category
     */
    public void edit(Category category) {
        String sql="update category set cname=? where cid=?";
        try {
            queryRunner.update(sql,category.getCname(),category.getCid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
