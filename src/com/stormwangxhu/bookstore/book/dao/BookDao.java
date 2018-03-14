package com.stormwangxhu.bookstore.book.dao;

import cn.itcast.jdbc.TxQueryRunner;
import com.stormwangxhu.bookstore.book.domain.Book;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class BookDao {
    QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 查询所有图书
     * @return
     */
    public List<Book> findAll(){
        String sql = "select * from book";
        try {
            return queryRunner.query(sql,new BeanListHandler<Book>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException("查询所有图书失败！");
        }
    }

    /**
     * 按分类查询所有图书
     * @param cid
     * @return
     */
    public List<Book> findByCategory(String cid) {
        String sql ="select * from book where cid =?";
        try {
            return queryRunner.query(sql,new BeanListHandler<Book>(Book.class),cid);
        } catch (SQLException e) {
            throw  new RuntimeException("按分类查询图书失败！");
        }
    }

    /**
     * 根据bid加载book信息
     * @param bid
     * @return
     */
    public Book findByBid(String bid) {
        String sql ="select * from book where bid=?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(Book.class),bid);
        } catch (SQLException e) {
            throw new RuntimeException("加载某本书信息错误！");
        }
    }

    /**
     * 查询指定分类下的图书本数
     * @param cid
     * @return
     */
    public int getCountByCid(String cid) {
        String sql ="select count(*) from book where cid=?";
        try {
            Number cnt=(Number) queryRunner.query(sql,new ScalarHandler(),cid);
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException("加载某本书信息错误！");
        }
    }
}
