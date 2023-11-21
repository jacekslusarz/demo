package com.jac.boot.demo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class SsoController {

  @GetMapping("/logout")
  String logout(HttpServletRequest request) throws ServletException {
    request.logout();
    return "index";
  }
}
