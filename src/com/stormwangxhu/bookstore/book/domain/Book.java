package com.stormwangxhu.bookstore.book.domain;

import com.stormwangxhu.bookstore.category.domain.Category;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class Book {
    private String bid ;
    private String bname ;
    private double price ;
    private String image ;
    private String cid ;
    private String author;
    private boolean del;//表示图书是否删除

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBname() {

        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBid() {

        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bid='" + bid + '\'' +
                ", bname='" + bname + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
