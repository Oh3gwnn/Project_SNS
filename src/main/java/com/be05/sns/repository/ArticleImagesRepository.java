package com.be05.sns.repository;

import com.be05.sns.entity.ArticleImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleImagesRepository extends JpaRepository<ArticleImages, Long> {
    ArticleImages findByIdAndArticleId_Id(Long id, Long articleId);
}