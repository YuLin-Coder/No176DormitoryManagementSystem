package com.silk.framework.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LindaSilk
 * @date 2021年3月09日, 周二
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    // 注册Token拦截器
    @Bean
    public TokenInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login");                // 针对除了login外的所有请求
    }

    // 使用CorsFilter解决跨域问题
    @Bean
    public CorsFilter corsFilter(){
        // 跨域配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");        // 允许跨域请求的域名
        corsConfiguration.addAllowedMethod("*");        // 允许跨域请求的方法
        corsConfiguration.addAllowedHeader("*");        // 允许跨域请求的头部

        // 获取跨域配置信息
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        CorsFilter corsFilter = new CorsFilter(urlBasedCorsConfigurationSource);
        return corsFilter;
    }
}
