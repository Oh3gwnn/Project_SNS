package com.be05.sns.repository;

import com.be05.sns.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByUserId_Username(String username, Pageable pageable);
    Optional<Article> findByIdAndUserId_Id(Long userid, Long articleId);
}