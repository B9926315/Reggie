package com.BYX.service.impl;

import com.BYX.mapper.ShoppingCartMapper;
import com.BYX.pojo.ShoppingCart;
import com.BYX.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author Planck
 * @Date 2023-01-08 - 13:01
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
