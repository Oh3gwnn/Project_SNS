package com.be05.sns.repository;

import com.be05.sns.entity.ArticleImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleImagesRepository extends JpaRepository<ArticleImages, Long> {
    ArticleImages findFirstByArticleId_IdOrderByIdAsc(Long articleId);
    List<ArticleImages> findAllByArticleId_Id(Long articleId);
}