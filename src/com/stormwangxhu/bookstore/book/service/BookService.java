package com.stormwangxhu.bookstore.book.service;

import com.stormwangxhu.bookstore.book.dao.BookDao;
import com.stormwangxhu.bookstore.book.domain.Book;

import java.util.List;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class BookService {
    BookDao bookDao = new BookDao();

    /**
     * 查询所有图书
     * @return
     */
    public List<Book> findAll(){
        return bookDao.findAll();
    }

    /**
     * 按分类查询所有图书
     * @param cid
     * @return
     */
    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }

    /**
     * 查询某一本书的详细信息
     * @param bid
     * @return
     */
    public Book load(String bid) {
        return bookDao.findByBid(bid);
    }
}
