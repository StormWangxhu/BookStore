package com.stormwangxhu.bookstore.cart.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 购物车类：条目的集合
 * @Author StormWangxhu
 * @Date Created in 2018/3/13
 */
public class Cart {
    //我的购物车，要顺序，所以要LinkedHashMap
    //其实，此处的map就是我们的购物车，后续的一系列操作都会应用map对象来操作
    //以book的bid做键，条目做值。
    private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();//存储条目

    /**
     * 添加条目到车中
     *
     * @param cartItem
     */
    public void add(CartItem cartItem) {
        //需要判断map中是否包含该条目
        if (map.containsKey(cartItem.getBook().getBid())) {
            //若该条目已经在map中，则得到该条目
            CartItem cartItem1 = map.get(cartItem.getBook().getBid());//得到老条目
            cartItem1.setCount(cartItem1.getCount() + cartItem.getCount());//设置老条目的数量为，其老条目数量+新条目的数量。
            map.put(cartItem.getBook().getBid(), cartItem1);//再放进去。
        } else {
            //若不存在，则我们加进去
            map.put(cartItem.getBook().getBid(), cartItem);

        }
    }

    /**
     * 清空所有条目
     */
    public void clear() {
        map.clear();
    }

    /**
     * 删除指定条目
     *
     * @param bid
     */
    public void delete(String bid) {
        map.remove(bid);
    }

    /**
     * 获取购物车所有条目
     *
     * @return
     */
    public Collection<CartItem> getCartItems() {
        return map.values();//该方法返回所有值，即所有条目
    }

    /**
     * 合计所有条目中书的总价钱。
     * @return
     */
    public double getTotal(){
        //合计=所有条目的小计之和！
        double total=0;
        for (CartItem cartItem:map.values()){
            total+=cartItem.getSubtotal();
        }
        return total;
    }


}
