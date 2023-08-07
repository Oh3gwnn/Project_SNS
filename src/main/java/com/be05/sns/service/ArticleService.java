package com.be05.sns.service;

import com.be05.sns.dto.AllArticleFromUserDto;
import com.be05.sns.dto.ArticleDto;
import com.be05.sns.entity.Article;
import com.be05.sns.entity.Users;
import com.be05.sns.repository.ArticleRepository;
import com.be05.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    // 1. create feed
    public void createFeed(ArticleDto dto, Authentication authentication) {
        Users user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        articleRepository.save(dto.newArticle(user));
    }
    // 2. writer all feed
    public Page<AllArticleFromUserDto> readAllFeed(Long userId, Authentication authentication) {
        Users writer = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Users viewer = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Article> articles = articleRepository
                .findAllByUserId_Username(writer.getUsername(), pageable);

        return articles.map(AllArticleFromUserDto::fromAllArticle);
    }
}