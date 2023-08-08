package com.be05.sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity
@Table(name = "like_article")
public class LikeArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article articleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    public LikeArticle newLikeThat(Users user, Article article) {
        LikeArticle like = new LikeArticle();
        like.setArticleId(article);
        like.setUserId(user);
        return like;
    }
}
