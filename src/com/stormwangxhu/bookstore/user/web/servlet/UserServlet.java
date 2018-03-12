package com.stormwangxhu.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import com.stormwangxhu.bookstore.user.domain.User;
import com.stormwangxhu.bookstore.user.domain.UserException;
import com.stormwangxhu.bookstore.user.service.UserService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/11
 */
@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {
    UserService userService = new UserService();

    /**
     * 注册功能
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    protected String regist(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //封装表单数据到User对象中
        User form = CommonUtils.toBean(req.getParameterMap(), User.class);
        //补uuid
        form.setUid(CommonUtils.uuid());
        //补状态码
        form.setCode(CommonUtils.uuid() + CommonUtils.uuid());

        /*
         * 2、输入校验,创建一个Map，用来封装错误信息，key为表单字段名称，值为错误信息。
         */
        Map<String, String> errorsMap = new HashMap<String, String>();
        //用户名校验
        String username = form.getUsername();
        if (username == null || username.trim().isEmpty()) {
            errorsMap.put("username", "用户名不能为空！");
        } else if (username.length() < 3 || username.length() > 10) {
            errorsMap.put("username", "用户名长度必须在3~10之间！");
        }

        //密码校验
        String password = form.getPassword();
        if (password == null || password.trim().isEmpty()) {
            errorsMap.put("password", "密码不能为空！");
        } else if (password.length() < 3 || password.length() > 10) {
            errorsMap.put("password", "密码长度必须在3~10之间！");
        }

        //邮箱校验
        String email = form.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errorsMap.put("email", "Email不能为空！");
        } else if (!email.matches("\\w+@\\w\\.\\w")) {//正则表达式校验
            errorsMap.put("email", "Email格式错误！");
        }

        /*
         * 3、判断是否存在错误信息
         */

        if (errorsMap.size() > 0) {
            //1、保存错误信息
            //2、保存表单数据
            //3、转发到regist.jsp
            req.setAttribute("errorsMap", errorsMap);
            req.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }

        /*
         * 4、调用service的regist方法
         */
        try {
            userService.regist(form);//没有抛出异常，说明执行成功，保存成功信息，并发邮件，进行激活！
        } catch (UserException e) {
            /*
             *1、保存异常信息
             * 2、保存form
             * 3.转发到regist.jsp
             */
            req.setAttribute("msg", e.getMessage());
            req.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }

        /*
         * 发邮件
         * 准备配置文件
         */

        //获取配置文件内容
        Properties properties = new Properties();
        properties.load(this.getClass().
                getClassLoader().getResourceAsStream("email_template.properties"));
        String host = properties.getProperty("host");//获取服务器主机名
        String uname = properties.getProperty("uname");//获取用户名
        String pwd = properties.getProperty("pwd");//获取密码
        String from = properties.getProperty("from");//获取发件人
        String to = form.getEmail();//获取收件人
        String subject = properties.getProperty("subject");//获取主题
        String content = properties.getProperty("content");//获取邮件内容
        content = MessageFormat.format(content, form.getCode());//将配置文件中的占位符{0}替换为状态码

        /*
         * 开始发
         */
        //得到session
        Session session = MailUtils.createSession(host, uname, pwd);
        //创建Mail对象
        Mail mail = new Mail(from, to, subject, content);
        try {
            MailUtils.send(session, mail);//发邮件
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        /*
         * 1、保存成功信息
         * 2、 转发到msg.jsp
         */
        req.setAttribute("msg", "恭喜，注册成功！请马上到邮箱进行激活！");
        return "f:/jsps/msg.jsp";
    }

    /**
     * 激活功能
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    protected String active(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1、获取表单中的激活码
         * 2、调用service的激活方法
         *    若抛出异常，保存异常信息到request域中，转发到msg.jsp
         * 3、若成功，保存成功信息到request域中，转发到msg.jsp
         */
        String code = req.getParameter("code");
        try {
            userService.active(code);
            req.setAttribute("msg", "恭喜！您激活成功了！请登录！");
        } catch (UserException e) {
            req.setAttribute("msg", e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }

    /**
     * 登录功能
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    protected String login(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1、封装表单数据到User中
         * 2、输入校验
         * 3、调用service的login方法
         *    若抛出异常，保存错误信息，form到request域中,转发到login.jsp
         * 4、保存成功信息到session中，然后重定向到index.jsp
         */
        User form=CommonUtils.toBean(req.getParameterMap(),User.class);
        Map<String, String> errorsMap = new HashMap<String, String>();
        //用户名校验
        String username = form.getUsername();
        if (username == null || username.trim().isEmpty()) {
            errorsMap.put("username", "用户名不能为空！");
        } else if (username.length() < 3 || username.length() > 10) {
            errorsMap.put("username", "用户名长度必须在3~10之间！");
        }

        //密码校验
        String password = form.getPassword();
        if (password == null || password.trim().isEmpty()) {
            errorsMap.put("password", "密码不能为空！");
        } else if (password.length() < 3 || password.length() > 10) {
            errorsMap.put("password", "密码长度必须在3~10之间！");
        }
        if (errorsMap.size() > 0) {
            //1、保存错误信息
            //2、保存表单数据
            //3、转发到regist.jsp
            req.setAttribute("errorsMap", errorsMap);
            req.setAttribute("form", form);
            return "f:/jsps/user/login.jsp";
        }

        try {
            User user=userService.login(form);
            //保存成功信息到session中
            req.getSession().setAttribute("session_user",user);
            //重定向到index.jsp
            return "r:/index.jsp";
        } catch (UserException e) {
            //保存错误信息、form到request域中
            req.setAttribute("msg",e.getMessage());
            req.setAttribute("form",form);//为了回显
            return "f:/jsps/user/login.jsp";
        }
    }

    /**
     * 退出功能
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    protected String quit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //退出功能，即销毁session
        req.getSession().invalidate();//销毁
        return "r:/index.jsp";

    }
}
