package com.example.springsecurity6.services;

import com.example.springsecurity6.dtos.tweet.CreateTweetRequest;
import com.example.springsecurity6.dtos.tweet.FeedDto;
import com.example.springsecurity6.dtos.tweet.TweetDto;
import com.example.springsecurity6.enuns.Role;
import com.example.springsecurity6.mappers.TweetMapper;
import com.example.springsecurity6.models.Tweet;
import com.example.springsecurity6.repositories.TweetRepository;
import com.example.springsecurity6.repositories.UserRepository;
import java.util.List;
import java.util.PrimitiveIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TweetService {

  private final UserRepository userRepository;

  @Autowired
  private TweetRepository tweetRepository;

  @Autowired
  private AuthService authService;

  private static final String FEED_SORT_PROPERTY = "timestamp";

  TweetService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

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
    var isThisUserAdmin = user.getRoles().stream().anyMatch(Role.ADMIN::equals);

    if (!isTweetFromThisUser && !isThisUserAdmin) {
      throw new RuntimeException("User not authorized to delete this tweet");
    }

    tweetRepository.deleteById(id);
  }

  public List<TweetDto> getAllTweets() {
    var tweets = tweetRepository.findAll();
    var tweetDtos = tweets.stream().map(TweetMapper::toDto).toList();
    return tweetDtos;
  }

  public FeedDto feed(int page, int pageSize) {
    var pageRequest = PageRequest.of(
      page,
      pageSize,
      Sort.Direction.DESC,
      FEED_SORT_PROPERTY
    );

    var tweets = tweetRepository.findAll(pageRequest);
    var tweetDtos = tweets.stream().map(TweetMapper::toDto).toList();
    var totalPages = tweets.getTotalPages();
    var totalElements = tweets.getTotalElements();

    var feed = new FeedDto(
      tweetDtos,
      page,
      pageSize,
      totalPages,
      totalElements
    );

    return feed;
  }
}
