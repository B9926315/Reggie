package com.BYX.service;

import com.BYX.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author Planck
 * @Date 2023-01-09 - 12:01
 */
public interface OrdersService extends IService<Orders> {
    /**
     * 用户下单
     */
    void submit(Orders orders);
}
