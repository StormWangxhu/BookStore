package com.stormwangxhu.bookstore.category.service;

import com.stormwangxhu.bookstore.category.dao.CategoryDao;
import com.stormwangxhu.bookstore.category.domain.Category;

import java.util.List;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class CategoryService {
    CategoryDao categoryDao = new CategoryDao();

    /**
     * 查询所有
     * @return
     */
    public List<Category> findAll(){
        return categoryDao.findAll();
    }
}
