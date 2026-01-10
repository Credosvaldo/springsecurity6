package com.example.springsecurity6.services;

import com.example.springsecurity6.dtos.tweet.CreateTweetRequest;
import com.example.springsecurity6.dtos.tweet.TweetDto;
import com.example.springsecurity6.mappers.TweetMapper;
import com.example.springsecurity6.models.Tweet;
import com.example.springsecurity6.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetService {

  @Autowired
  private TweetRepository tweetRepository;

  @Autowired
  private AuthService authService;

  public Tweet findById(Long id) {
    return tweetRepository.findById(id).orElseThrow();
  }

  public TweetDto createTweet(CreateTweetRequest createTweetRequest) {
    var content = createTweetRequest.content();
    var currentUser = authService.getCurrentUser();

    var newTweet = new Tweet(content, currentUser);
    newTweet = tweetRepository.save(newTweet);

    var tweetDto = TweetMapper.toDto(newTweet);

    return tweetDto;
  }

  public void deleteTweet(Long id) {
    var user = authService.getCurrentUser();
    var tweet = findById(id);

    var isTweetFromThisUser = tweet.getUser().getId().equals(user.getId());

    if (!isTweetFromThisUser) {
      throw new RuntimeException("User not authorized to delete this tweet");
    }

    tweetRepository.deleteById(id);
  }
}
