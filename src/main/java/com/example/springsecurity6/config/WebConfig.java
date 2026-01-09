package com.example.springsecurity6.config;

import com.example.springsecurity6.builder.UserBuilder;
import com.example.springsecurity6.enuns.Role;
import com.example.springsecurity6.repositories.TweetRepository;
import com.example.springsecurity6.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer, CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TweetRepository tweetRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    var user1 = UserBuilder.builder()
      .username("Pedro")
      .password(passwordEncoder.encode("123"))
      .roles(List.of(Role.ADMIN))
      .build();

    var user2 = UserBuilder.builder()
      .username("php")
      .password(passwordEncoder.encode("1234"))
      .roles(List.of(Role.BASIC))
      .build();

    var userList = List.of(user1, user2);

    userRepository.saveAll(userList);
  }
}
