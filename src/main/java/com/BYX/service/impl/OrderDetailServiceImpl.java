package com.BYX.service.impl;

import com.BYX.mapper.OrderDetailMapper;
import com.BYX.pojo.OrderDetail;
import com.BYX.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author Planck
 * @Date 2023-01-09 - 13:25
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
