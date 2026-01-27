package com.example.itau_backend.common.logger;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggerRequestFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    long start = System.currentTimeMillis();

    try {
      filterChain.doFilter(request, response);
    } finally {

      log.info("Request: {}{} | Response: {} | Duration: {}ms",
          request.getMethod(),
          request.getRequestURI(),
          response.getStatus(),
          System.currentTimeMillis() - start);
    }
  }
}
