package com.example.springsecurity6.controllers;

import com.example.springsecurity6.dtos.users.UserDto;
import com.example.springsecurity6.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserDto> getMe() {
    var userResponse = userService.getMe();
    return ResponseEntity.ok(userResponse);
  }

  @GetMapping("/all")
  // @PreAuthorize("hasRole('ADMIN')")
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  public ResponseEntity<List<UserDto>> getAll() {
    var users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }
}
