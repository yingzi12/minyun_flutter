package com.xinshijie.gallery.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter(RedisTemplate<String, String> redisTemplate) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(redisTemplate, "yourSecretKey"));
        registrationBean.addUrlPatterns("/*"); // Set the URL patterns for the filter
        return registrationBean;
    }
}

