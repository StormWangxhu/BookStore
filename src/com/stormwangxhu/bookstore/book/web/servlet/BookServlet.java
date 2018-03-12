package com.stormwangxhu.bookstore.book.web.servlet;

import cn.itcast.servlet.BaseServlet;
import com.stormwangxhu.bookstore.book.domain.Book;
import com.stormwangxhu.bookstore.book.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
@WebServlet(name = "BookServlet")
public class BookServlet extends BaseServlet {
    BookService bookService = new BookService();

    /**
     * 查询所有图书
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("bookList", bookService.findAll());
        return "f/jsps/book/list.jsp";
    }

    /**
     * 按分类查询所有图书
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        bookService.findByCategory(request.getParameter("cid"));//分类编号
        return "f:/jsps/book/list.jsp";
    }

    public String load(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Book book = bookService.load(request.getParameter("bid"));
        request.setAttribute("book",book);
        return "f:/jsps/book/desc.jsp";

    }
}