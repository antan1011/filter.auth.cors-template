package com.example.system.config.filter;

import com.example.system.utils.SimpleDateFormatHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;

@Component
@Slf4j
public class AppRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        log.info("Method: {}\tUri:{}\trequestTime:{}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), SimpleDateFormatHolder.get().format(new Date()));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void printHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String temp;
        System.out.println("*****************************");
        while (Objects.nonNull((temp = headerNames.nextElement()))) {
            System.out.println(temp + ":\t" + request.getHeader(temp));
        }
        System.out.println("*****************************");
    }
}
