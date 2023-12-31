package com.example.system.config.filter;

import com.example.system.config.exception.AppException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;

@Component
public class AppAuthFilter implements Filter {
    private final RedisTemplate<String, String> redisTemplate;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public AppAuthFilter(RedisTemplate<String, String> redisTemplate, HandlerExceptionResolver handlerExceptionResolver) {
        this.redisTemplate = redisTemplate;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 如果请求头为空，仅get方法可用
        String key = "admin_auth";
        String adminAuth = httpServletRequest.getHeader(key);
        String method = httpServletRequest.getMethod().toUpperCase();
        if (Objects.equals(method, "GET") && Objects.isNull(adminAuth)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else if (Objects.nonNull(adminAuth)) {
            String authCode = redisTemplate.opsForValue().get(key);
            if (Objects.equals(adminAuth, authCode)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else if (Objects.equals(adminAuth, "Not Logged In")) {
                handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null, new AppException(401, "未登录！"));
            } else {
                handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null, new AppException(4001, "Bug！"));
            }
        } else {
            if(httpServletRequest.getRequestURI().startsWith("/record/upload_image")) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }

    }
}
