package com.stormwangxhu.bookstore.category.service;

import com.stormwangxhu.bookstore.category.dao.CategoryDao;
import com.stormwangxhu.bookstore.category.domain.Category;
import com.stormwangxhu.bookstore.book.dao.BookDao;
import com.stormwangxhu.bookstore.category.web.servlet.admin.CategoryException;

import java.util.List;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class CategoryService {
    CategoryDao categoryDao = new CategoryDao();
    BookDao bookDao = new BookDao();

    /**
     * 查询所有
     *
     * @return
     */
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    /**
     * 后台管理之添加分类
     * @param category
     */
    public void add(Category category) {
        categoryDao.add(category);
    }

    /**
     * 后台管理之删除分类
     * @param cid
     */
    public void delete(String cid) throws CategoryException{
        //获取该分类下图书的本数
        int count = bookDao.getCountByCid(cid);
        //若该分类下存在图书，不让删除，我们抛出异常
        if (count>0) throw new CategoryException("改分类下还有图书，不能删除！");
        //删除该分类
        categoryDao.delete(cid);

    }

    /**
     * 加载分类
     * @param cid
     * @return
     */
    public Category load(String cid) {
        return categoryDao.load(cid);
    }

    /**
     * 修改分类
     * @param category
     */
    public void edit(Category category) {
        categoryDao.edit(category);

    }
}
