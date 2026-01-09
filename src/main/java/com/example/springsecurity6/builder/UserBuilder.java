package com.example.springsecurity6.builder;

import com.example.springsecurity6.enuns.Role;
import com.example.springsecurity6.models.Tweet;
import com.example.springsecurity6.models.Users;
import java.util.List;

public class UserBuilder {

  private Long id = null;
  private String username = "Usuario 1";
  private String password = "123";
  private List<Tweet> tweets = List.of();
  private List<Role> roles = List.of(Role.BASIC);

  public static UserBuilder builder() {
    return new UserBuilder();
  }

  public Users build() {
    return new Users(id, username, password, tweets, roles);
  }

  public UserBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public UserBuilder username(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder password(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder tweets(List<Tweet> tweets) {
    this.tweets = tweets;
    return this;
  }

  public UserBuilder roles(List<Role> roles) {
    this.roles = roles;
    return this;
  }
}
