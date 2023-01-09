package com.BYX.service.impl;

import com.BYX.mapper.EmployeeMapper;
import com.BYX.pojo.Employee;
import com.BYX.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author Bai YanXu
 * @Date 2023-01-02 - 15:36
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{

}
