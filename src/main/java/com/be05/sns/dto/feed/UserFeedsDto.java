package com.be05.sns.dto.feed;

import com.be05.sns.entity.Article;
import lombok.Builder;
import lombok.Data;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class UserFeedsDto {
    private String userName;
    private String title;
    private String createdAt;
    private String thumbnail;

    public static UserFeedsDto fromAllFeed(Article article) {
        return UserFeedsDto.builder()
                .userName(article.getUserId().getUsername())
                .title(article.getTitle())
                .createdAt(article.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("MM월 dd일(E) a HH시 mm분")))
                .thumbnail(article.getThumbnail())
                .build();
    }
}