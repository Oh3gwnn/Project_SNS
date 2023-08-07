package com.be05.sns.dto.Article;

import com.be05.sns.entity.Article;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class AUserFeedDto {
    private String userName;
    private String title;
    private String content;
    private String createdAt;

    public static AUserFeedDto fromFeedInfo(Article article) {
        return AUserFeedDto.builder()
                .userName(article.getUserId().getUsername())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("MM월 dd일(E) a HH시 mm분")))
                .build();
    }
}
