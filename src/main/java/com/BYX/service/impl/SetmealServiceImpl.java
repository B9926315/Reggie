package com.BYX.service.impl;

import com.BYX.common.CustomException;
import com.BYX.dto.DishDto;
import com.BYX.dto.SetmealDto;
import com.BYX.mapper.SetmealMapper;
import com.BYX.pojo.Setmeal;
import com.BYX.pojo.SetmealDish;
import com.BYX.service.SetmealDishService;
import com.BYX.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Bai YanXu
 * @Date 2023-01-03 - 21:23
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐，同时保存套餐与菜品关系
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes=setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }
    /**
     * 删除套餐，同时删除套餐与菜品关系
     * @param ids 套餐ID数组
     */
    @Override
    @Transactional
    public void deleteWithDish(List<Long> ids) {
        //查询套餐状态，查看是否有值
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if (count>0){
            //有正在售卖中的的套餐，不能删除
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        //if条件不成立，可以删除
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper1);
    }
}
