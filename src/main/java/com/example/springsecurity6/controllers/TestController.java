package com.example.springsecurity6.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping
  public ResponseEntity<String> getMethodName() {
    return ResponseEntity.ok("Hello, World! The application is running.");
  }

  @PostMapping
  public ResponseEntity<String> postMethodName() {
    return ResponseEntity.ok("POST request received successfully.");
  }

}
