package com.be05.sns.dto;

import com.be05.sns.entity.Article;
import com.be05.sns.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private Article articleId;
    private Users userId;

    public CommentDto newComment(Users user, Article article) {
        return CommentDto.builder()
                .content(content)
                .userId(user)
                .articleId(article)
                .build();
    }
}
