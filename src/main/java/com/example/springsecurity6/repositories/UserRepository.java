package com.example.springsecurity6.repositories;

import com.example.springsecurity6.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {}
