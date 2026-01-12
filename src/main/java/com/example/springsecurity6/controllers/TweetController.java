package com.example.springsecurity6.controllers;

import com.example.springsecurity6.dtos.tweet.CreateTweetRequest;
import com.example.springsecurity6.dtos.tweet.FeedDto;
import com.example.springsecurity6.dtos.tweet.TweetDto;
import com.example.springsecurity6.services.TweetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweets")
public class TweetController {

  @Autowired
  private TweetService tweetService;

  @GetMapping("/all")
  public ResponseEntity<List<TweetDto>> getAllTweets() {
    var tweets = tweetService.getAllTweets();
    return ResponseEntity.ok(tweets);
  }

  @GetMapping("/feed")
  public ResponseEntity<FeedDto> getFeed(
    @RequestParam(defaultValue = "0") Integer page,
    @RequestParam(defaultValue = "10") Integer pageSize
  ) {
    var feed = tweetService.feed(page, pageSize);
    return ResponseEntity.ok(feed);
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
