package com.tk.tliaswebmanagment.filter;

import com.tk.tliaswebmanagment.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//用用rilter这种传统的方式来实现拦截器的功能，但要记得在引导类上添加@ServletComponentScan注解
//    来扫描并注册Filter组件

@Slf4j
//@WebFilter("/*")
public class TokenFilter implements Filter {
//    存在默认的空实现，可以不写
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //判断是否是login
        String requestURI = request.getRequestURI();
        boolean isLogin = requestURI.contains("login");
        if(isLogin) {
            //放行
            filterChain.doFilter(servletRequest, servletResponse);
            log.info("登录请求，放行");
            return;
        }
        //解析token
        String token = request.getHeader("token");
        if(token==null || token.isEmpty()){
            log.info("token为空");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401未授权
            return;
        }
        Claims claims = null;
        try {
            claims = JwtUtils.parseToken(token);
        } catch (Exception e){
            log.info("token非法");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401未授权
            return;
        }
        //放行
        log.info("token合法，放行");
        log.info("欢迎{}",claims.get("username"));
        filterChain.doFilter(servletRequest, servletResponse);
    }

//    存在默认的空实现，可以不写
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
}
