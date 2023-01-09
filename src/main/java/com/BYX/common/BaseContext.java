package com.BYX.common;

/**
 * @Author Bai YanXu
 * @Date 2023-01-03 - 16:41
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static long getCurrentId(){
        return threadLocal.get();
    }
}
