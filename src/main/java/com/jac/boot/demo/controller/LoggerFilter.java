package com.jac.boot.demo.controller;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class LoggerFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response,
      final FilterChain chain) throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      var httpRequest = (HttpServletRequest) request;
      logger.info("[doFilter] " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
    }
    chain.doFilter(request, response);
  }
}
