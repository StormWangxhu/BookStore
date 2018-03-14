package com.stormwangxhu.bookstore.order.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import com.stormwangxhu.bookstore.cart.domain.Cart;
import com.stormwangxhu.bookstore.cart.domain.CartItem;
import com.stormwangxhu.bookstore.order.domain.Order;
import com.stormwangxhu.bookstore.order.domain.OrderItem;
import com.stormwangxhu.bookstore.order.service.OrderException;
import com.stormwangxhu.bookstore.order.service.OrderService;
import com.stormwangxhu.bookstore.user.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/13
 */
@WebServlet(name = "OrderServlet")
public class OrderServlet extends BaseServlet {
    OrderService orderService = new OrderService();

    /**
     * 添加订单
     * 把session中的车拿来用来生成order对象
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1、从session中得到cart对象
         * 2、使用cart生成order对象
         * 3、调用service方法完成添加订单
         * 4、保存order到request域中，
         */
        //1、从session中获取cart
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //把Cart对象转换为Order对象
        /*
         * 2、创建order对象，并设置属性
         * Cart------>Order
         */
        Order order = new Order();
        order.setOid(CommonUtils.uuid());//设置编号
        order.setOrdertime(new Date());//设置下单时间
        order.setState(1);//设置订单状态为1，表示未付款。
        User user = (User) request.getSession().getAttribute("session_user");//从session中获取当前订单所有者
        order.setOwner(user);//设置订单所有者
        order.setTotal(cart.getTotal());//设置订单的合计，从cart中获取
        /*
         * 创建订单条目集合
         * cartItemList----->orderItemList
         */
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        //循环遍历Cart中所有CartItem,使用每一个CartItem对象创建OrderItem对象，并添加到集合中1
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setIid(CommonUtils.uuid());//设置条目的id
            orderItem.setSubtotal(cartItem.getSubtotal());//设置条目的小计
            orderItem.setBook(cartItem.getBook());//设置条目的图书
            orderItem.setCount(cartItem.getCount());//设条置目的数量
            orderItem.setOrder(order);//设置所属订单

            orderItemList.add(orderItem);//把订单条目添加到集合中
        }

        //把所有的订单条目添加到订单中
        order.setOrderItemList(orderItemList);

        //清空购物车
        cart.clear();

        /*
         * 3、调用orderService添加订单
         */
        orderService.add(order);
        /*
         * 4、保存order到request域中，转发到/jsps/order/desc.jsp
         */
        request.setAttribute("order", order);

        return "f:/jsps/order/desc.jsp";
    }

    /**
     * 我的订单
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String myOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("session_user");
        List<Order> orderList = orderService.myOrders(user.getUid());
        request.setAttribute("orderList", orderList);
        return "f:/jsps/order/list.jsp";
    }

    /**
     * 加载订单
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String load(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1、得到oid
         * 2、使用oid调用service方法得到order
         * 3、保存到request域中，转发/jsps/order/desc.jsp
         */
        String oid = request.getParameter("oid");
        request.setAttribute("order", orderService.load(oid));
        return "f:/jsps/order/desc.jsp";
    }

    /**
     * 确认收货
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1. 获取oid参数
         * 2. 调用service方法
         *   > 如果有异常，保存异常信息，转发到msg.jsp
         * 3. 保存成功信息，转发到msg.jsp
         */
        String oid = request.getParameter("oid");
        try {
            orderService.confirm(oid);
            request.setAttribute("msg", "恭喜！交易成功！");
        } catch (OrderException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }


}
