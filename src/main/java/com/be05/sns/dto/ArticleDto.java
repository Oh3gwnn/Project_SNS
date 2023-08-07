package com.be05.sns.dto;

import com.be05.sns.entity.Article;
import com.be05.sns.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private Boolean draft;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    private String userName;

    // 새로운 article 생성
    public Article newArticle(Users user) {
        Article article = new Article();
        article.setUserId(user);
        article.setTitle(title);
        article.setContent(content);
        article.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return article;
    }
}