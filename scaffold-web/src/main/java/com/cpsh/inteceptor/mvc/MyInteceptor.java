package com.cpsh.inteceptor.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInteceptor implements HandlerInterceptor {

    /**
     * 最后执行，可用于释放资源,返回处理(已经渲染了页面)
     * 可以根据ex是否为null判断是否发生了异常，进行日志记录
     * 参数中的Object handler是下一个拦截器。
     */
//    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        System.out.println("执行afterCompletion(),最后执行，可用于释放资源,返回处理(已经渲染了页面)------------------------");

    }

    
    /**
     * 生成视图之前执行,后处理(调用了Service并返回ModelAndView，但未进行页面渲染)
     * 有机会修改ModelAndView
     * 参数中的Object handler是下一个拦截器。
     */
//    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        System.out.println("执行postHandle(),生成视图之前执行,后处理(调用了Service并返回ModelAndView，但未进行页面渲染)------------------------");

    }

    /**
     * Action之前执行,预处理,
     * 可以进行编码、安全控制等处理
     * 参数中的Object handler是下一个拦截器。
     */
//    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2) throws Exception {
        System.out.println("执行preHandle(),Action之前执行,预处理,Action之前执行,预处理,----------------------------");
        return true;
    }

}
