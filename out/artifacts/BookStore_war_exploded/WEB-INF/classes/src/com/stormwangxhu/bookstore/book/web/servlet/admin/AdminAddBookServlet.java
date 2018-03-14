package com.stormwangxhu.bookstore.book.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import com.stormwangxhu.bookstore.book.domain.Book;
import com.stormwangxhu.bookstore.book.service.BookService;
import com.stormwangxhu.bookstore.category.domain.Category;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/14
 */
@WebServlet(name = "AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
    BookService bookService = new BookService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 1、把表单数据封装到Book对象中
         *    * 上传三步
         */
        //创建工厂    图片缓存大小为15kb，
        DiskFileItemFactory factory = new DiskFileItemFactory(15*1024,new File("f:/temp"));
        //得到解析器
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        //设置单个文件大小为15KB
        servletFileUpload.setFileSizeMax(15*1024);
        //使用解析器去解析request对象，得到List<FileItem>
        try {
            List<FileItem> fileItemList =servletFileUpload.parseRequest(request);
            /*
             * 把fileItemList中的数据封装到Book对象中
             *   * 把所有的普通表单字段数据先封装到Map中
             *   * 再把Map中的数据封装到Book中
             */
            //把fileItemList中除了文件以外所有的普通数据封装到map中
            Map<String,String> map = new HashMap<String, String>();
            for (FileItem fileItem:fileItemList){
                if (fileItem.isFormField()){
                    map.put(fileItem.getFieldName(),fileItem.getString("UTF-8"));
                }
            }
            Book book = CommonUtils.toBean(map,Book.class);

            //为book指定bid
            book.setBid(CommonUtils.uuid());

            /*
             * 需要把Map中的cid封装到Category中，再把Category赋值给Book
             */
            Category category=CommonUtils.toBean(map,Category.class);
            book.setCategory(category);

            /*
             * 2、保存上传的图片
             *   * 保存的目录
             *   * 保存的文件名称
             */
            //得到保存的目录
            String savePath = this.getServletContext().getRealPath("/book_img");
            //得到上传文件名称,为解决文件名称冲突，前加uuid
            String filename = CommonUtils.uuid()+"_"+fileItemList.get(1).getName();

            /*
             * 校验文件的拓展名
             */
            if (filename.toLowerCase().endsWith("jpg")){
                request.setAttribute("msg","您上传的图片不是JPG拓展名！");
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
                        .forward(request,response);
                return;
            }

            //使用目录和文件创建目标文件
            File destFile=new File(savePath,filename);
            //保存上传文件到目标文件中
            fileItemList.get(1).write(destFile);

            /*
             * 3、设置Book对象的image,即把图片的路径设置给Book的image
             */
            book.setImage("book_img/"+filename);
            /*
             * 4、使用bookService的add()方法完成保存
             */
            bookService.add(book);


            /*
             * 校验图片的尺寸
             */
            Image image  = new ImageIcon(destFile.getAbsolutePath()).getImage();
            if (image.getWidth(null)>200||image.getHeight(null)>200){
                destFile.delete();//删除这个文件
                request.setAttribute("msg","您上传的图片尺寸超出了200*200！");
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
                        .forward(request,response);
                return;
            }

            /*
             * 5、返回到图书列表
             */
            request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll").forward(request,response);


        } catch (Exception e) {
            if (e instanceof FileUploadBase.FileSizeLimitExceededException){
                request.setAttribute("msg","您上传的图片超出了15KB");
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
                        .forward(request,response);
            }
        }
    }


}
