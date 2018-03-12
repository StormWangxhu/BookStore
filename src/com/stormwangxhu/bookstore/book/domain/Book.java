package com.stormwangxhu.bookstore.book.domain;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class Book {
    private String bid ;
    private String bname ;
    private String price ;

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

    private String image ;
    private String cid ;

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

    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {
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
}
