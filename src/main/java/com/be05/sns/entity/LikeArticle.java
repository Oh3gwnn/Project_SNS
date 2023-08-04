package com.be05.sns.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity
@Table(name = "like_article")
public class LikeArticle {
    @Id
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article articleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;
}
