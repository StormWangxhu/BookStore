package com.stormwangxhu.bookstore.category.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
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
 * @Date Created in 2018/3/14
 */
@WebServlet(name = "AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
   private CategoryService categoryService= new CategoryService();

    /**
     * 后台分类管理之查询所有分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1、调用service方法，完成查询所有分类
         * 2、保存request域中，转发到/adminjsps/admin/category/list.jsp
         */
        List<Category> categoryList =categoryService.findAll();
        request.setAttribute("categoryList",categoryList);
        return "f:/adminjsps/admin/category/list.jsp";
    }

    /**
     * 后台分类管理之添加分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         /*
          * 1、分装表单数据
          * 2、补全：cid
          * 3、调用service方法完成添加工作
          * 4、调用findAll
          */
         Category category= CommonUtils.toBean(request.getParameterMap(),Category.class);
         category.setCid(CommonUtils.uuid());
         categoryService.add(category);
         //添加记录后，又将数据库中所有记录查询一遍。
         return findAll(request,response);
    }


    /**
     * 后台管理之删除分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1、获取参数cid
         * 2、调用service方法，传递cid
         *   若抛出异常，保存异常信息
         * 3、调用findAll()
         */
        String cid=request.getParameter("cid");
        try {
            categoryService.delete(cid);
            return findAll(request,response);
        } catch (CategoryException e) {
            request.setAttribute("msg",e.getMessage());
            return "f/adminjsps/msg.jsp";
        }
    }

    /**
     * 修改分类之前的加载工作
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String editPre(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cid =request.getParameter("cid");
        request.setAttribute("category",categoryService.load(cid));
        return "f:/adminjsps/admin/category/mod.jsp";
    }

    /**
     * 修改分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1、分装表单数据
         * 2、调用service方法完成修改
         * 3、调用findAll()
         */
        Category category = CommonUtils.toBean(request.getParameterMap(),Category.class);
        categoryService.edit(category);
        return findAll(request,response);
    }
}
