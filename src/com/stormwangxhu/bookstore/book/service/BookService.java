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

    /**
     * 后台管理之添加图书
     * @param book
     */
    public void add(Book book) {
        bookDao.add(book);
    }

    /**
     * 后台管理之删除图书
     * @param bid
     */
    public void delete(String bid){
        bookDao.delete(bid);
    }

    /**
     * 后台管理之修改图书
     * @param book
     */
    public void edit(Book book) {
        bookDao.edit(book);
    }
}
