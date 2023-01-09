package com.BYX.dto;

import com.BYX.pojo.Dish;
import com.BYX.pojo.DishFlavor;
import com.BYX.pojo.Dish;
import com.BYX.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
