package com.BYX.dto;

import com.BYX.common.R;
import com.BYX.pojo.Setmeal;
import com.BYX.pojo.SetmealDish;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

}
