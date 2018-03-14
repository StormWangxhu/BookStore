package com.stormwangxhu.bookstore.order.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import com.stormwangxhu.bookstore.book.domain.Book;
import com.stormwangxhu.bookstore.order.domain.Order;
import com.stormwangxhu.bookstore.order.domain.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/13
 */
public class OrderDao {
    QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 添加订单
     *
     * @param order
     */
    public void addOrder(Order order) {
        try {
            String sql = "insert into orders values(?,?,?,?,?,?)";
            /*
             * 此处要处理一个问题：
             * 即处理util的Date 转换为sql的timestamp
             */
            Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
            Object[] params = {order.getOid(), timestamp, order.getTotal(),
                    order.getState(), order.getOwner().getUid(), order.getAddress()};
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入订单条目
     *
     * @param orderItemList
     */
    public void addOrderItemList(List<OrderItem> orderItemList) {
        /*
         *  QueryRunner类的batch(String sql,Object[][] params)   batch批量
         *  其中，params是一个多维数组
         *  每一个一维数组都与sql在一起执行一次，多个一维数组就执行多次
         */
        try {
            String sql = "insert into orderitem values(?,?,?,?,?)";
            /*
             * 把orderItemList转换成两维数组
             *   把一个OrderItem对象转换成一个一维数组
             */
            Object[][] params = new Object[orderItemList.size()][];
            //循环遍历orderItemList,使用每个orderItem对象为params中每一个数组赋值。
            for (int i = 0; i < orderItemList.size(); i++) {
                OrderItem orderItem = orderItemList.get(i);
                params[i] = new Object[]{orderItem.getIid(), orderItem.getCount(), orderItem.getSubtotal(),
                        orderItem.getOrder().getOid(), orderItem.getBook().getBid()};
            }
            queryRunner.batch(sql, params);//执行批处理
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按uid查询订单
     *
     * @param uid
     * @return
     */
    public List<Order> findByUid(String uid) {
        /*
         * 1、通过uid查询出当前用户的所有List<Order>
         * 2、循环遍历每个Order,为其加载他的所有OrderItem
         */
        try {
            //1、得到当前用户的所有订单
            String sql = "select * from orders where uid=?";
            List<Order> orderList = queryRunner.query(sql, new BeanListHandler<Order>(Order.class));

            //2、循环遍历所有Order,为其加载它所有的订单条目
            for (Order order : orderList) {
                loadOrderItems(order);//为order对象加载它的所有订单条目
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 加载指定的订单所有的订单条目
     *
     * @param order
     */
    private void loadOrderItems(Order order) {
        /*
         * 查询两张表
         */
        String sql = "select * from orderitem i,book b where i.bid=b.bid and oid=?";
        /*
         * 因为一行结果集对应的不再是一个javabean,所以不能再使用BeanListHandler,而是MapListHandler
         */
        try {
            List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(), order.getOid());
            List<OrderItem> orderItemList = toOrderItemList(mapList);
            order.setOrderItemList(orderItemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把mapList中每个Map,转换为两个对象，并建立关系
     *
     * @param mapList
     * @return
     */
    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (Map<String, Object> map : mapList) {
            OrderItem item = toOrderItem(map);
            orderItemList.add(item);
        }
        return orderItemList;
    }

    /**
     * 把一个Map转换成一个OrderItem对象
     *
     * @param map
     * @return
     */
    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Book book = CommonUtils.toBean(map, Book.class);
        orderItem.setBook(book);
        return orderItem;
    }

    /**
     * 加载订单
     *
     * @param oid
     * @return
     */
    public  Order load(String oid) {
        String sql = "select * from orders where oid=?";
        try {
            Order order = queryRunner.query(sql, new BeanHandler<Order>(Order.class));
            //为order加载它的所有条目
            loadOrderItems(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过oid查询订单状态
     * @param oid
     * @return
     */
    public int getStsteByOid(String oid) {
        String sql="select * from orders where oid=?";
        try {
            return (Integer) queryRunner.query(sql,new ScalarHandler(),oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改订单状态
     * @param oid
     * @param state
     * @return
     */
    public void updateState(String oid ,int state){
        String sql="update orders set state=? where oid=?";
        try {
            queryRunner.update(sql,state,oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
