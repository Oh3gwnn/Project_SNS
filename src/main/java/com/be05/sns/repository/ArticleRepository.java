package com.be05.sns.repository;

import com.be05.sns.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByUserId_Username(String username, Pageable pageable);
    Page<Article> findAllByUserId_UsernameIn(List<String> usernames, Pageable pageable);
}