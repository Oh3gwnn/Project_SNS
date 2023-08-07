package com.be05.sns.controller;

import com.be05.sns.dto.Article.AUserFeedDto;
import com.be05.sns.dto.Article.UserFeedsDto;
import com.be05.sns.dto.ResponseDto;
import com.be05.sns.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class ArticleController {
    private final ResponseDto response;
    private final ArticleService articleService;

    @PostMapping(value = "/create")
    public ResponseDto createdFeed(
            @RequestParam String title, @RequestParam String content,
            @RequestPart(value = "image", required = false) List<MultipartFile> imageFiles,
            Authentication authentication) {
        articleService.createFeed(title, content, imageFiles, authentication);
        return response.toMessage("피드를 생성했습니다.");
    }

    @GetMapping("/read/{userId}")
    public Page<UserFeedsDto> readFeed(@PathVariable("userId") Long userId,
                                       Authentication authentication) {
        return articleService.readAllFeed(userId, authentication);
    }

    @GetMapping("/read/{userId}/{articleId}")
    public AUserFeedDto readFeed(@PathVariable("userId") Long userId,
                                 @PathVariable("articleId") Long articleId,
                                 Authentication authentication) {
        return articleService.read(userId, articleId, authentication);
    }
}