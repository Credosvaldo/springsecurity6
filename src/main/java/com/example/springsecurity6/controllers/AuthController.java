package com.example.springsecurity6.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity6.dtos.auth.LoginRequest;
import com.example.springsecurity6.dtos.auth.LoginResponse;
import com.example.springsecurity6.dtos.auth.RegisterRequest;
import com.example.springsecurity6.dtos.auth.RegisterResponse;
import com.example.springsecurity6.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
  
  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
    var loginResponse = authService.login(loginRequest);
    return ResponseEntity.ok(loginResponse);
  }

  @PostMapping("/register")
  public ResponseEntity<LoginResponse> register(
    RegisterRequest registerRequest
  ) {
    var loginResponse = authService.register(registerRequest);
    return ResponseEntity.ok(loginResponse);
  }
}
