package com.be05.sns.repository;

import com.be05.sns.entity.LikeArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeArticle, Long> {
    LikeArticle findByUserId_UsernameAndArticleId_Id(String userName, Long articleId);
}