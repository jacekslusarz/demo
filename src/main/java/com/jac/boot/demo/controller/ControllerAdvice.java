package com.jac.boot.demo.controller;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = IllegalProcessingException.class)
public class ControllerAdvice {

}
