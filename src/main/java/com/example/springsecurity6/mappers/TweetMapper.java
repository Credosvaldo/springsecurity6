package com.example.springsecurity6.mappers;

import com.example.springsecurity6.dtos.tweet.TweetDto;
import com.example.springsecurity6.models.Tweet;

public class TweetMapper {

  public static TweetDto toDto(Tweet tweet) {
    return new TweetDto(
        tweet.getId(),
        tweet.getContent(),
        tweet.getUser().getUsername(),
        tweet.getTimestamp().toString()
    );
  }
}
