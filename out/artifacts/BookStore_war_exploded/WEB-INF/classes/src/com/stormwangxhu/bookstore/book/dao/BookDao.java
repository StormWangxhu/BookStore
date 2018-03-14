package com.stormwangxhu.bookstore.book.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import com.stormwangxhu.bookstore.book.domain.Book;
import com.stormwangxhu.bookstore.category.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class BookDao {
    QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 查询所有图书
     *
     * @return
     */
    public List<Book> findAll() {
        String sql = "select * from book where del=false";
        try {
            return queryRunner.query(sql, new BeanListHandler<Book>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException("查询所有图书失败！");
        }
    }

    /**
     * 按分类查询所有图书
     *
     * @param cid
     * @return
     */
    public List<Book> findByCategory(String cid) {
        String sql = "select * from book where cid =? and del=false";
        try {
            return queryRunner.query(sql, new BeanListHandler<Book>(Book.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException("按分类查询图书失败！");
        }
    }

    /**
     * 根据bid加载book信息
     *
     * @param bid
     * @return
     */
    public Book findByBid(String bid) {
        String sql = "select * from book where bid=?";
        try {
            /*
             * 我们需要在Book对象中保存Category的信息。
             */
            Map<String, Object> map = queryRunner.query(sql, new MapHandler(), bid);
            /*
             * 使用一个Map映射出两个对象，然后再给这2个对象建立关系。
             */
            Category category = CommonUtils.toBean(map, Category.class);
            Book book = CommonUtils.toBean(map, Book.class);
            book.setCategory(category);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 查询指定分类下的图书本数
     *
     * @param cid
     * @return
     */
    public int getCountByCid(String cid) {
        String sql = "select count(*) from book where cid=?";
        try {
            Number cnt = (Number) queryRunner.query(sql, new ScalarHandler(), cid);
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException("加载某本书信息错误！");
        }
    }

    /**
     * 后管理之添加图书
     *
     * @param book
     */
    public void add(Book book) {
        String sql = "insert into book values(?,?,?,?,?,?)";
        Object[] params = {book.getBid(), book.getBname(), book.getPrice(),
                book.getAuthor(), book.getImage()};
        try {
            queryRunner.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 后台管理之删除图书，一般都为假删除。即将数据库表中的del修改值为true
     * @param bid
     */
    public void delete(String bid){
        String sql="update book set del=true where bid=?";
        try {
            queryRunner.update(sql,bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param book
     */
    public void edit(Book book) {
        String sql="update book set bname=?, price=?,author=?, image=?, cid=? where bid=?";
        try {
            Object[] params = {book.getBname(), book.getPrice(),
                    book.getAuthor(), book.getImage(),
                    book.getCategory().getCid(), book.getBid()};
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
