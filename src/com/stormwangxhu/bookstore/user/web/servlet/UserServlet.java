package com.stormwangxhu.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import com.stormwangxhu.bookstore.user.domain.User;
import com.stormwangxhu.bookstore.user.domain.UserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/11
 */
@WebServlet(name = "UserServlet" )
public class UserServlet extends HttpServlet {

    protected void regist(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //封装表单数据到User对象中
        User form = CommonUtils.toBean(req.getParameterMap(),User.class);
        //补uuid
        form.setUid(CommonUtils.uuid());
        //补状态码
        form.setCode(CommonUtils.uuid()+CommonUtils.uuid());

        /*
         * 输入校验
         */
        Map<String,String> errorsMap =new HashMap<String, String>();
        String username = form.getUsername();
        if (username==null||username.trim().isEmpty()){
            errorsMap.put("username","用户名不能为空！");
        }else if (username.length()<3||username.length()>10){
            errorsMap.put("username","用户名长度必须在3~10之间！");
        }

    }
}
