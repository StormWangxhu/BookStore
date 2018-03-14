package com.stormwangxhu.bookstore.order.domain;

import com.stormwangxhu.bookstore.book.domain.Book;

/**
 * @Description: 订单条目类
 * @Author StormWangxhu
 * @Date Created in 2018/3/13
 */
public class OrderItem {
    private String iid;
    private int count ;//订单数量
    private double subtotal ;//小计
    private Order order;//所属订单
    private Book book;//所要购买的图书

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Order getOrder() {

        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getSubtotal() {

        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getCount() {

        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIid() {

        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "iid='" + iid + '\'' +
                ", count=" + count +
                ", subtotal=" + subtotal +
                ", order=" + order +
                ", book=" + book +
                '}';
    }
}
