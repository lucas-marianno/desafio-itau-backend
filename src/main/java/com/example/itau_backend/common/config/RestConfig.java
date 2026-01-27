package com.example.itau_backend.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.itau_backend.health.interceptor.RequestInterceptor;

@Configuration
public class RestConfig implements WebMvcConfigurer {

  @Autowired
  private RequestInterceptor requestInterceptor;

  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestInterceptor);
  }

}
