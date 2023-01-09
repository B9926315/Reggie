package com.BYX.service.impl;

import com.BYX.common.CustomException;
import com.BYX.mapper.CategoryMapper;
import com.BYX.pojo.*;
import com.BYX.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Bai YanXu
 * @Date 2023-01-03 - 17:23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 删除菜品，删除之前先做判断是否有相关的菜
     *
     * @param id 分类套餐ID
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishQueryWrapper);
        if (count > 0) {
            //已经关联菜品，禁止删除
            throw new CustomException("当前分类关联了菜品，禁止删除");
        }
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(setmealQueryWrapper);
        if (count1 > 0) {
            //已经关联套餐，禁止删除
            throw new CustomException("当前分类关联了套餐，禁止删除");
        }
        super.removeById(id);
    }
}
