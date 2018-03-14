package com.stormwangxhu.bookstore.book.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import com.stormwangxhu.bookstore.book.domain.Book;
import com.stormwangxhu.bookstore.book.service.BookService;
import com.stormwangxhu.bookstore.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/14
 */
@WebServlet(name = "AdminBookServlet")
public class AdminBookServlet extends BaseServlet {

    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

    /**
     * 后台图书管理之查询所有图书
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("bookList", bookService.findAll());
        return "f:/adminjsps/admin/book/list.jsp";
    }

    /**
     * 后台图书管理之加载图书
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
         * 1、获取参数bid,调用service方法得到Book对象
         * 2、获取所有分类，保存到request域中
         * 3、保存book到request域中，转发到desc.jsp
         */
        String bid = request.getParameter("bid");
        Book book = bookService.load(bid);
        request.setAttribute("book", book);
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/book/desc.jsp";
    }

    /**
     * 添加图书之前加载所有分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addPre(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 查询所有分类，保存到request域中，转发到add.jsp
         * add.jsp中把所有分类使用下拉列表显示在表单中
         */
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/book/add.jsp";
    }

    /**
     * 后台管理之删除图书
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bid=request.getParameter("bid");
        bookService.delete(bid);
        return findAll(request,response);
    }

    public String edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Book book= CommonUtils.toBean(request.getParameterMap(),Book.class);
        bookService.edit(book);
        return findAll(request,response);
    }

}
