package com.BYX;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author Bai YanXu
 * @Date 2023-01-01 - 21:58
 */
@Slf4j
@SpringBootApplication
//配置过滤器，扫描过滤器注解
@ServletComponentScan
//开启事务注解支持
@EnableTransactionManagement
//开启缓存注解功能
@EnableCaching
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功");
    }
}
