package com.stormwangxhu.bookstore.category.web.servlet;

import com.stormwangxhu.bookstore.category.domain.Category;
import com.stormwangxhu.bookstore.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
@WebServlet(name = "CategoryServlet")
public class CategoryServlet extends HttpServlet {
    CategoryService categoryService = new CategoryService();

    /**
     * 查询所有
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    protected String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setAttribute("categoryList",categoryService.findAll());
        return "f:/jsps/left.jsp";
    }
}
