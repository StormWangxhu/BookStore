CREATE DATABASE bookstore;

/*用户表*/
CREATE TABLE tb_user(
  uid CHAR(32) PRIMARY KEY,/*主键*/
  username VARCHAR(50) NOT NULL,/*用户名*/
  `password` VARCHAR(50) NOT NULL,/*密码*/
  email VARCHAR(50) NOT NULL,/*邮箱*/
  `code` CHAR(64) NOT NULL,/*激活码*/
  state BOOLEAN/*用户状态，有两种是否激活*/
);

SELECT * FROM tb_user;

/*分类*/
CREATE TABLE category (
  cid CHAR(32) PRIMARY KEY,/*主键*/
  cname VARCHAR(100) NOT NULL/*分类名称*/
);

INSERT  INTO category(cid,cname) VALUES ('1','JavaSE');
INSERT  INTO category(cid,cname) VALUES ('2','JavaEE');
INSERT  INTO category(cid,cname) VALUES ('3','Javascript');

SELECT * FROM category;

/*图书表*/
CREATE TABLE book (
  bid CHAR(32) PRIMARY KEY,/*主键*/
  bname VARCHAR(100),/*图书名*/
  price DECIMAL(5,1),/*单价*/
  author VARCHAR(20),/*作者*/
  image VARCHAR(200),/*图片*/
  cid CHAR(32),/*所属分类*/
  FOREIGN KEY (cid) REFERENCES category(cid)/*建立主外键关系*/
);

INSERT  INTO book VALUES ('1','Java编程思想（第4版）','75.6','qdmmy6','book_img/9317290-1_l.jpg','1');
INSERT  INTO book VALUES ('2','Java核心技术卷1','68.5','qdmmy6','book_img/20285763-1_l.jpg','1');
INSERT  INTO book VALUES ('3','Java就业培训教程','39.9','张孝祥','book_img/8758723-1_l.jpg','1');
INSERT  INTO book VALUES ('4','Head First java','47.5','（美）塞若','book_img/9265169-1_l.jpg','1');
INSERT  INTO book VALUES ('5','JavaWeb开发详解','83.3','孙鑫','book_img/22788412-1_l.jpg','2');
INSERT  INTO book VALUES ('6','Struts2深入详解','63.2','孙鑫','book_img/20385925-1_l.jpg','2');
INSERT  INTO book VALUES ('7','精通Hibernate','30.0','孙卫琴','book_img/8991366-1_l.jpg','2');
INSERT  INTO book VALUES ('8','精通Spring2.x','63.2','陈华雄','book_img/20029394-1_l.jpg','2');
INSERT  INTO book VALUES ('9','Javascript权威指南','93.6','（美）弗兰纳根','book_img/22722790-1_l.jpg','3');

SELECT * FROM book;

/*订单表*/
CREATE TABLE orders (
  oid CHAR(32) PRIMARY KEY,/*主键*/
  ordertime DATETIME,/*订单生成时间*/
  total DECIMAL(10,0),/*订单合计*/
  state SMALLINT(1),/*订单状态：未付款、已付款但未发货、已发货但未确认收货、收货已结束*/
  uid CHAR(32),/*订单的主人*/
  address VARCHAR(200),/*订单的收货地址*/
  FOREIGN KEY (uid) REFERENCES tb_user(uid)/*建立主外键关系*/
);

SELECT * FROM orders;

/*订单项表*/
CREATE TABLE orderitem (
  iid CHAR(32) PRIMARY KEY,/*主键*/
  `count` INT,/*数量*/orderitem
  subtotal DECIMAL(10,0),/*小计*/
  oid CHAR(32),/*所属订单*/
  bid CHAR(32),/*订单项所指的商品*/
  FOREIGN KEY (oid) REFERENCES orders (oid),/*建立主外键关系*/
  FOREIGN KEY (bid) REFERENCES book (bid)/*建立主外键关系*/
);

SELECT * FROM orderitem;