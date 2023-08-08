package com.be05.sns.controller;

import com.be05.sns.dto.ResponseDto;
import com.be05.sns.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final ResponseDto response;
    private final LikeService likeService;

    @PostMapping("/feed/read/{articleId}/like")
    public ResponseDto likeArticle(@PathVariable("articleId") Long articleId,
                                   Authentication authentication) {
        return response.toMessage(likeService.like(articleId, authentication));
    }
}