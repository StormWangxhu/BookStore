package com.stormwangxhu.bookstore.user.web.filter;

import com.stormwangxhu.bookstore.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description: 添加前台登录过滤器:过滤和购物车和订单相关的servlet和jsp
 * @Author StormWangxhu
 * @Date Created in 2018/3/14
 */
@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /*
         * 1、从session中获取用户信息
         *
         */
        HttpServletRequest httpServletRequest=(HttpServletRequest) req;
        User user = (User) httpServletRequest.getSession().getAttribute("session_user");
        if (user!=null){
            chain.doFilter(req,resp);
        }else {
            httpServletRequest.setAttribute("msg","您还没有登录！");
            httpServletRequest.getRequestDispatcher("/jsps/user/login.jsp").forward(httpServletRequest,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
