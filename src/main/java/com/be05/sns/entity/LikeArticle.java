package com.be05.sns.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "like_article")
public class LikeArticle {
    @EmbeddedId
    private LikeId id;

    @MapsId("articleId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article articleId;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Users userId;

    public LikeArticle newLikeThat(Users user, Article article, LikeId likeId) {
        LikeArticle like = new LikeArticle();
        like.setId(likeId);
        like.setArticleId(article);
        like.setUserId(user);
        return like;
    }
}
