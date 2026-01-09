package com.example.springsecurity6.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
  @Schema(example = "Pedro") String username,

  @Schema(example = "123") String password
) {}
