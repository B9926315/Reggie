package com.BYX.service;

import com.BYX.dto.DishDto;
import com.BYX.dto.SetmealDto;
import com.BYX.pojo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author Bai YanXu
 * @Date 2023-01-03 - 21:21
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时保存套餐与菜品关系
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐与菜品关系
     * @param ids 套餐ID数组
     */
    void deleteWithDish(List<Long> ids);
}
