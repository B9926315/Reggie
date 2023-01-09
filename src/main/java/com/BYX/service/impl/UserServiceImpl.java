package com.BYX.service.impl;

import com.BYX.mapper.UserMapper;
import com.BYX.pojo.User;
import com.BYX.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author Planck
 * @Date 2023-01-06 - 16:59
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
