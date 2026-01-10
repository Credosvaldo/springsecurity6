package com.example.springsecurity6.services;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springsecurity6.dtos.auth.LoginRequest;
import com.example.springsecurity6.dtos.auth.LoginResponse;
import com.example.springsecurity6.dtos.auth.RegisterRequest;
import com.example.springsecurity6.enuns.Role;
import com.example.springsecurity6.models.Users;
import com.example.springsecurity6.repositories.UserRepository;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtEncoder jwtEncoder;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Value("${spring.application.name}")
  private String appName;

  private final Long ONE_HOUR = 3600L;
  private final Long EXPIRES_TIME = ONE_HOUR * 3;

  public LoginResponse login(LoginRequest loginRequest) {
    var username = loginRequest.username();
    var password = loginRequest.password();

    var user = userRepository.findByUsername(username);

    if (user == null || !isLoginCorrect(password, user.getPassword())) {
      throw new BadCredentialsException("Invalid username or password");
    }

    var now = Instant.now();
    var expiresAt = now.plusSeconds(EXPIRES_TIME);

    var scopes = user
      .getRoles()
      .stream()
      .map(Role::name)
      .collect(Collectors.joining(" "));

    var claims = JwtClaimsSet.builder()
      .issuer(appName)
      .subject(username)
      .issuedAt(now)
      .expiresAt(expiresAt)
      .claim("scope", scopes)
      .build();

    var jwtValue = jwtEncoder
      .encode(JwtEncoderParameters.from(claims))
      .getTokenValue();

    var loginResponse = new LoginResponse(jwtValue, EXPIRES_TIME);

    return loginResponse;
  }

  @Transactional
  public LoginResponse register(RegisterRequest registerRequest) {
    var username = registerRequest.username();
    var password = registerRequest.password();
    var encondedPassword = passwordEncoder.encode(password);

    var userAlreadyExists = userRepository.findByUsername(username) != null;

    if (userAlreadyExists) {
      throw new IllegalArgumentException("Username already exists");
    }

    var newUser = new Users(username, encondedPassword, Role.BASIC);
    newUser = userRepository.save(newUser);
    var loginRequest = new LoginRequest(username, password);
    var loginResponse = login(loginRequest);

    return loginResponse;
  }

  public Users getCurrentUser() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication == null || !authentication.isAuthenticated()) {
      throw new IllegalStateException("No authenticated user found");
    }

    var username = authentication.getName();
    var user = userRepository.findByUsername(username);

    return user;
  }

  private boolean isLoginCorrect(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
