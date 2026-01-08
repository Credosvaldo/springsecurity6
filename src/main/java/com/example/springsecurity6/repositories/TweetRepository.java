package com.example.springsecurity6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecurity6.models.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
