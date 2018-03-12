package com.stormwangxhu.bookstore.category.dao;

import cn.itcast.jdbc.TxQueryRunner;
import com.stormwangxhu.bookstore.category.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class CategoryDao {
    QueryRunner queryRunner= new TxQueryRunner();

    /**
     * 查询所有
     * @return
     */
    public List<Category> findAll(){
        String sql="select * from category";
        try {
            return queryRunner.query(sql,new BeanListHandler<Category>(Category.class));
        } catch (SQLException e) {
            throw new RuntimeException("查询所有分类失败！");
        }
    }
}
