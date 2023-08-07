package com.be05.sns.dto;

import com.be05.sns.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    private Users userId;

    // 새로운 article 생성
    public ArticleDto newArticle(Users user, String title, String content) {
        return ArticleDto.builder()
                .userId(user)
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}