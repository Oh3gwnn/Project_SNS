package com.be05.sns.dto.Article;

import com.be05.sns.entity.Article;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
public class AUserFeedDto {
    private String userName;
    private String title;
    private String content;
    private String createdAt;
    private List<String> imageUrls;

    public static AUserFeedDto fromFeedInfo(Article article, List<String> imageUrls) {
        return AUserFeedDto.builder()
                .userName(article.getUserId().getUsername())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("MM월 dd일(E) a HH시 mm분")))
                .imageUrls(imageUrls)
                .build();
    }
}
