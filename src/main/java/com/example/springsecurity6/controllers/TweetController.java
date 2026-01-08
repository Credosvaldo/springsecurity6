package com.example.springsecurity6.controllers;

import com.example.springsecurity6.models.Tweet;
import java.time.Instant;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweets")
public class TweetController {

  public ResponseEntity<List<Tweet>> getAllTweets() {
    return ResponseEntity.ok()
      .body(List.of(new Tweet(1L, "Sample tweet", null, Instant.now())));
  }
}
