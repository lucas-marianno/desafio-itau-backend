package com.example.itau_backend.health.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.itau_backend.health.service.HealthService;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ServerRequestInterceptor implements HandlerInterceptor {

  private final HealthService healthService;

  ServerRequestInterceptor(HealthService healthService) {
    this.healthService = healthService;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {

    healthService.registerServerRequestTimestamp();
  }

}
