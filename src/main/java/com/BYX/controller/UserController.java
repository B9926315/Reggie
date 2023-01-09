package com.BYX.controller;

import com.BYX.common.R;
import com.BYX.pojo.User;
import com.BYX.service.UserService;
import com.BYX.utils.SMSUtils;
import com.BYX.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author Planck
 * @Date 2023-01-06 - 17:00
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     *  用户登录发送验证码
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpServletRequest request){
        String phone = user.getPhone().trim();
        if (StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //SMSUtils.sendMessage("Planck","SMS_267755109",phone,code);
            log.info("手机号："+phone);
            log.info("验证码: "+code);
            request.getSession().setAttribute(phone,code);
            return  R.success("验证码发送成功");
        }
        return R.error("短信发送失败");
    }
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String,String> map, HttpSession session){
        //获取手机号
        String phone = map.get("phone");
        log.info("获取的手机号："+phone);
        //获取验证码
        String code = map.get("code");
        log.info("前端传回的验证码："+code);
        //获取Session中的手机号
        Object codeInSession = session.getAttribute(phone);
        log.info("Session中的值："+codeInSession.toString());
        if (codeInSession!=null && codeInSession.equals(code)){
            //登录成功
            //判断是否为新用户，若是，自动注册
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if (user==null){
                user=new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            //if条件不成立，该用户已经注册过
            return R.success(user);
        }
        return R.error("登录失败");
    }
}
