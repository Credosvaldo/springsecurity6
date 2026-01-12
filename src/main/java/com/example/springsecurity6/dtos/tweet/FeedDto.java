package com.example.springsecurity6.dtos.tweet;

import java.util.List;

public record FeedDto(
  List<TweetDto> feedItens,
  int page,
  int pageSize,
  int totalPages,
  long totalElements
) {}
