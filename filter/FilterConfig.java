package com.example.system.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class FilterConfig{

	@Bean
	public FilterRegistrationBean<AppCorsFilter> corsFilter(AppCorsFilter corsFilter){
		FilterRegistrationBean<AppCorsFilter> filterRegistrationBean = new FilterRegistrationBean<>(corsFilter);
//		filterRegistrationBean.setFilter(new CorsFilter());//设置过滤器名称
		filterRegistrationBean.addUrlPatterns();//配置过滤规则
		filterRegistrationBean.setOrder(1); //order的数值越小 则优先级越高
		return filterRegistrationBean;
	}
	@Bean
	public FilterRegistrationBean<AppAuthFilter> authFilter(AppAuthFilter authFilter){
		String[] authPath = {"/article/*","/record/*","/utils/*","/site/*","/task/*"};
		FilterRegistrationBean<AppAuthFilter> filterRegistrationBean = new FilterRegistrationBean<>(authFilter);
//		filterRegistrationBean.setFilter(new AuthFilter());
		filterRegistrationBean.addUrlPatterns(authPath);
		filterRegistrationBean.setOrder(2);
		return filterRegistrationBean;
	}
	@Bean
	public FilterRegistrationBean<AppRequestFilter> requestFilter(AppRequestFilter requestFilter) {
		FilterRegistrationBean<AppRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>(requestFilter);
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setOrder(3);
		return filterRegistrationBean;
	}

}
