package com.tk.tliaswebmanagment.interceptor;

import com.tk.tliaswebmanagment.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//注册拦截器要自己定义一个配置类



//定义拦截器
//这些方法都有自己的默认实现方法
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    //控制器之前运行，返回ture则放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("token");
        if(token==null || token.isEmpty()){
            log.info("token为空");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401未授权
            return false;
        }
        //解析token
        Claims claims = null;
        try {
            claims = JwtUtils.parseToken(token);
        } catch (Exception e){
            log.info("token非法");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401未授权
            return false;
        }
        //放行
        log.info("token合法，放行");
        log.info("欢迎{}",claims.get("username"));
        return true;
    }

    //目标资源方法运行之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //视图渲染之后运行，一般没用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
