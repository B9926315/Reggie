package com.BYX.mapper;

import com.BYX.pojo.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Bai YanXu
 * @Date 2023-01-03 - 21:19
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    void updateStatusByIds(@Param("ids") String[] ids,@Param("status") Integer status);
}
