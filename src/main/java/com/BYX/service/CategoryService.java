package com.BYX.service;

import com.BYX.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author Bai YanXu
 * @Date 2023-01-03 - 17:22
 */
public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
