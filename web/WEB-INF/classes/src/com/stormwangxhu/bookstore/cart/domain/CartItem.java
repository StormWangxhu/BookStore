package com.stormwangxhu.bookstore.cart.domain;

import com.stormwangxhu.bookstore.book.domain.Book;

import java.math.BigDecimal;

/**
 * @Description: 购物车条目类：包含商品+数量
 * @Author StormWangxhu
 * @Date Created in 2018/3/13
 */
public class CartItem {
    private Book book;//书本
    private int count;//数量

    /**
     * 合计，一种书的价钱
     *
     * @return
     */
    /*
     * 处理二进制运算问题的误差
     */
    public double getSubtotal() {
        //小计方法，但它没有对应成员
        BigDecimal price = new BigDecimal(book.getPrice() + "");
        BigDecimal count1 = new BigDecimal(count + "");
        return price.multiply(count1).doubleValue();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Book getBook() {

        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", count=" + count +
                '}';
    }
}
