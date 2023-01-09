package com.BYX.controller;

import com.BYX.common.R;
import com.BYX.pojo.Orders;
import com.BYX.service.OrdersService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Planck
 * @Date 2023-01-09 - 12:03
 */
@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 生成订单
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 分页查询订单数据
     */
    @GetMapping("/page")
    public R<Page<Orders>> page(int page,int pageSize){
        Page<Orders> pageInfo=new Page<>(page,pageSize);
        Page<Orders> ordersPage = ordersService.page(pageInfo);
        return R.success(ordersPage);
    }
}
