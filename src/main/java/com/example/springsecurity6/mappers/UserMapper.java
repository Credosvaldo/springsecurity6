package com.example.springsecurity6.mappers;

import com.example.springsecurity6.dtos.users.UserDto;
import com.example.springsecurity6.models.Users;

public class UserMapper {

  public static UserDto toDto(Users user) {
    return new UserDto(user.getId(), user.getUsername());
  }
}
