package com.BYX.mapper;

import com.BYX.pojo.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Bai YanXu
 * @Date 2023-01-04 - 14:46
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
    /**
     * 删除口味信息
     * @param ids 菜品ID数组
     */
    void deleteWithFlavor(@Param("ids") String[] ids);
}
