package com.BYX.controller;

import com.BYX.common.R;
import com.BYX.pojo.Employee;
import com.BYX.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @Author Bai YanXu
 * @Date 2023-01-02 - 15:38
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     *  员工登录操作
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1.密码MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        //查一下此用户存不存在
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp==null){
            return R.error("用户名或密码错误");
        }
        //比对密码
        if (!emp.getPassword().equals(password)){
            return R.error("用户名或密码错误");
        }
        if (emp.getStatus()==0){
            return R.error("账号已锁定");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 安全退出
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     *  新增员工
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        //为新员工设置初始密码，MD5加密的
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //保存数据
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     *  分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page pageInfo=new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     *根据员工ID修改员工信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     *根据员工ID查询详细信息
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }
        return R.error("没有查到员工信息");
    }
}
