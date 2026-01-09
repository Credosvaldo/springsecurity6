package com.example.springsecurity6.dtos.auth;

public record LoginResponse(String token, Long expiresIn) {}
