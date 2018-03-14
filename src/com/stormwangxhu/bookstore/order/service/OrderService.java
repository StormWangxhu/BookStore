package com.stormwangxhu.bookstore.order.service;

import cn.itcast.jdbc.JdbcUtils;
import com.stormwangxhu.bookstore.order.dao.OrderDao;
import com.stormwangxhu.bookstore.order.domain.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/13
 */
public class OrderService {
    OrderDao orderDao = new OrderDao();

    /**
     * 添加订单
     * 需要处理事务
     * @param order
     */
    public void add(Order order){
        try {
            //开启事务
            JdbcUtils.beginTransaction();
            orderDao.addOrder(order);//插入订单
            orderDao.addOrderItemList(order.getOrderItemList());//插入订单中所有条目
            //提交事务
            JdbcUtils.commitTransaction();
        }catch (Exception e){
            //回滚事务
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 我的订单
     * @param uid
     * @return
     */
    public List<Order> myOrders(String uid){
        return orderDao.findByUid(uid);
    }

    /**
     *加载订单
     * @param oid
     * @return
     */
    public Order load(String oid) {
        return orderDao.load(oid);
    }

    /**
     * 确认订单
     * @param oid
     * @throws OrderException
     */
    public void confirm(String oid) throws OrderException{
        /*
         * 1、校验订单状态，如果不是3，抛出异常
         */
        int state =orderDao.getStsteByOid(oid);
        if (state!=3)throw new OrderException("订单确认失败!");
        /*
         * 2、修改订单状态为4，表示交易成功
         */
        orderDao.updateState(oid,4);
    }
}
