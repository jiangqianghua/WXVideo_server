package com.jqh;

import com.jqh.controller.interceptor.MiniInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
// 映射本地资源文件
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/META-INF/resources/")
//                .addResourceLocations("file:/Users/jiangqianghua/Downloads/apache-tomcat-7.0.75/webapps/wxvideos/");
//
//    }

    @Bean
    public MiniInterceptor miniInterceptor(){
        return new MiniInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册一个拦截器
        registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
                                                .addPathPatterns("/video/upload","/video/uploadCover")
                                                .addPathPatterns("/video/userLike","/video/userUnLike")
                                                .excludePathPatterns("/user/queryPublisher");
                                                //.addPathPatterns("/bgm/**");
        super.addInterceptors(registry);
    }
}
