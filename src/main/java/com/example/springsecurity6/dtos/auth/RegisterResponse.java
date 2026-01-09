package com.example.springsecurity6.dtos.auth;

public record RegisterResponse(String token, Long expiresIn) {
  
}
