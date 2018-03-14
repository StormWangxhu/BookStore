package com.stormwangxhu.bookstore.cart.web.servlet;

import cn.itcast.servlet.BaseServlet;
import com.stormwangxhu.bookstore.book.domain.Book;
import com.stormwangxhu.bookstore.book.service.BookService;
import com.stormwangxhu.bookstore.cart.domain.Cart;
import com.stormwangxhu.bookstore.cart.domain.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/13
 */
@WebServlet(name = "CartServlet")
public class CartServlet extends BaseServlet {

    /**
     * 添加购物车条目
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1、得到车(从session中得到，在用户登录时，给用户一量车)
         * 2、得到条目
         * 3、把条目添加到车中
         */
        /*
         * 1、得到车
         */
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        /*
         * 表单传递的只有bid和数量
         * 2、得到条目
         *     得到图书和数量
         *     先得到图书bid，然后通过bid查询数据库得到Book
         *     数量表单中有
         */
        String bid = request.getParameter("bid");
        Book book = new BookService().load(bid);
        int count = Integer.getInteger(request.getParameter("count"));
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setCount(count);

        /*
         * 3、把条目添加到车中
         */
        cart.add(cartItem);
        return "f:/jsps/cart/list.jsp";
    }

    /**
     * 清空购物条目
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String clear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.clear();
        return "f:/jsps/cart/list.jsp";
    }

    /**
     * 删除购物条目
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1、得到车
         * 2、得到要删除的bid
         */
        Cart cart=(Cart) request.getSession().getAttribute("cart");
        String bid = request.getParameter("bid");
        cart.delete(bid);
        return "f:/jsps/cart/list.jsp";
    }

}
