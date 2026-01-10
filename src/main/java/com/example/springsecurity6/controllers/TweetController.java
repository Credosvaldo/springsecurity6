package com.example.springsecurity6.controllers;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity6.dtos.tweet.CreateTweetRequest;
import com.example.springsecurity6.dtos.tweet.TweetDto;
import com.example.springsecurity6.models.Tweet;
import com.example.springsecurity6.services.TweetService;

@RestController
@RequestMapping("/tweets")
public class TweetController {

  @Autowired
  private TweetService tweetService;

  @GetMapping("/all")
  public ResponseEntity<List<Tweet>> getAllTweets() {
    return ResponseEntity.ok()
      .body(List.of(new Tweet(1L, "Sample tweet", null, Instant.now())));
  }

  @PostMapping
  public ResponseEntity<TweetDto> createTweet(
    @RequestBody CreateTweetRequest createTweetRequest
  ) {
    TweetDto responseDto = tweetService.createTweet(createTweetRequest);
    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTweet(@PathVariable Long id) {
    tweetService.deleteTweet(id);
    return ResponseEntity.ok().build();
  }
}
