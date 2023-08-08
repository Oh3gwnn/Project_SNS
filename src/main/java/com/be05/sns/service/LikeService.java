package com.be05.sns.service;

import com.be05.sns.entity.Article;
import com.be05.sns.entity.LikeArticle;
import com.be05.sns.entity.LikeId;
import com.be05.sns.entity.Users;
import com.be05.sns.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final GetObjService getObj;
    private final LikeRepository likeRepository;

    public String like(Long articleId, Authentication authentication) {
        Article article = getObj.getArticle(articleId);
        Users user = getObj.getUser(authentication.getName());
        // 자신의 피드는 좋아요 X
        if (user.getPassword().equals(article.getUserId().getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        LikeId likeId = new LikeId(article.getId(), user.getId());
        LikeArticle likeByUser = likeRepository
                .findByUserId_UsernameAndArticleId_Id(user.getUsername(), articleId);

        if (likeByUser == null) {
            LikeArticle like = new LikeArticle().newLikeThat(user, article, likeId);
            likeRepository.save(like);
            return "💖";
        } else {
            likeRepository.delete(likeByUser);
            return "💔";
        }
    }
}