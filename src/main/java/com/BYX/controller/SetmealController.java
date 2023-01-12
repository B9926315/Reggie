package com.BYX.controller;

import com.BYX.common.R;
import com.BYX.dto.SetmealDto;
import com.BYX.pojo.Category;
import com.BYX.pojo.Setmeal;
import com.BYX.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.jmx.snmp.SnmpUnknownModelLcdException;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Planck
 * @Date 2023-01-05 - 20:16
 */
@RestController
@RequestMapping("/setmeal")
//文档说明
@Api(tags = "套餐接口")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation(value = "新增套餐接口")
    @CacheEvict(value = "setmeal",allEntries = true) //清理所有缓存数据
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 分页查询数据
     * @param page 当前页码
     * @param pageSize 每页显示条数
     * @param name 查询关键字
     */
    @GetMapping("/page")
    @ApiOperation("分页展示套餐接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页码",readOnly = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",readOnly = true),
            @ApiImplicitParam(name = "name",value = "套餐名称",readOnly = false),
    })
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo=new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage=new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);
        //对象复制
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> setmealDtoList=records.stream().map((item)->{
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(setmealDtoList);
        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     * @param ids 被删除的套餐ID集合
     */
    @DeleteMapping
    @CacheEvict(value = "setmeal",allEntries = true) //清理所有缓存数据
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.deleteWithDish(ids);
        return R.success("套餐删除成功");
    }

    /**
     * 更改套餐售卖状态
     * @param status 套餐将被设置的状态
     * @param ids 套餐ID集合
     */
    @PostMapping("/status/{status}")
    @CacheEvict(value = "setmeal",allEntries = true) //清理所有缓存数据
    public R<String> updateStatus(@PathVariable Integer status,@RequestParam List<Long> ids){
        LambdaUpdateWrapper<Setmeal> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.in(Setmeal::getId,ids);
        updateWrapper.set(Setmeal::getStatus,status);
        setmealService.update(null,updateWrapper);
        return R.success("状态更新成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId+'_'+#setmeal.status")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }
}
