package com.BYX.service;

import com.BYX.dto.DishDto;
import com.BYX.pojo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Bai YanXu
 * @Date 2023-01-03 - 21:22
 */
public interface DishService extends IService<Dish> {
    //新增菜品，同时新增口味数据,需要操作的表：dish、dish_flavor
    void saveWithFlavor(DishDto dishDto);
    //根据菜品ID查询菜品详细信息与口味信息
    DishDto getByIdWithFlavor(Long id);
    //更新菜品详细信息与口味信息
    void updateWithFlavor(DishDto dishDto);
    //更新菜品状态
    void updateStatusByIds(String[] ids,Integer status);
    //删除菜品与口味信息
    void deleteByIdsWithFlavor(String[] ids);
}
