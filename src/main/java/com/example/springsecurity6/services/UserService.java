package com.example.springsecurity6.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsecurity6.dtos.users.UserDto;
import com.example.springsecurity6.mappers.UserMapper;
import com.example.springsecurity6.models.Users;
import com.example.springsecurity6.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public Users getCurrentUser() {
    return new Users();
  }

  public UserDto getMe() {
    var currentUser = getCurrentUser();
    var userDto = UserMapper.toDto(currentUser);
    return userDto;
  }

  public List<UserDto> getAllUsers() {
    var users = userRepository
      .findAll()
      .stream()
      .map(UserMapper::toDto)
      .toList();

    return users;
  }
}
