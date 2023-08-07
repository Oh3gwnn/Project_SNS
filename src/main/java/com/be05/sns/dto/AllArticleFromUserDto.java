package com.be05.sns.dto;

import com.be05.sns.entity.Article;
import lombok.Builder;
import lombok.Data;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class AllArticleFromUserDto {
    private String userName;
    private String title;
    private String createdAt;

    public static AllArticleFromUserDto fromAllArticle(Article article) {
        return AllArticleFromUserDto.builder()
                .userName(article.getUserId().getUsername())
                .title(article.getTitle())
                .createdAt(article.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("MM월 dd일(E) a HH시 mm분")))
                .build();
    }
}