package com.BYX.service.impl;

import com.BYX.dto.DishDto;
import com.BYX.mapper.DishFlavorMapper;
import com.BYX.mapper.DishMapper;
import com.BYX.pojo.Dish;
import com.BYX.pojo.DishFlavor;
import com.BYX.service.DishFlavorService;
import com.BYX.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Bai YanXu
 * @Date 2023-01-03 - 21:22
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    /**
     * 新增菜品，同时保存口味数据
     */
    @Override
    //操作多张表，开启事务功能
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品基本信息到表dish
        this.save(dishDto);
        //获得菜品ID
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors=flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        //保存菜品口味数据到表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     *  根据菜品ID查询菜品详细信息与口味信息
     * @param id 菜品ID
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        //查询出口味信息List
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    /**
     * 更新菜品详细信息与口味信息
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);
        //先从dish_flavor表中删除所有的口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        //重新添加口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        //flavors集合中没有菜品ID
        flavors=flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 更新菜品状态
     */
    @Override
    public void updateStatusByIds(String[] ids, Integer status) {
        dishMapper.updateStatusByIds(ids,status);
    }

    /**
     *  删除菜品与口味信息
     * @param ids 菜品ID数组
     */
    @Override
    @Transactional
    public void deleteByIdsWithFlavor(String[] ids) {
        //删除菜品基本表中指定数据
        List<String> idList = new ArrayList<>(Arrays.asList(ids));
        this.removeByIds(idList);
        //删除口味表数据
        dishFlavorMapper.deleteWithFlavor(ids);
    }
}
