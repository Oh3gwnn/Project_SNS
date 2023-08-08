package com.be05.sns.service;

import com.be05.sns.entity.Article;
import com.be05.sns.entity.Comment;
import com.be05.sns.entity.UserFollows;
import com.be05.sns.entity.Users;
import com.be05.sns.repository.ArticleRepository;
import com.be05.sns.repository.CommentRepository;
import com.be05.sns.repository.FollowRepository;
import com.be05.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GetObjService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;

    // 해당 유저 불러오기(이름)
    public Users getUser(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
    }

    // 해당 유저 불러오기(아이디)
    public Users getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // 해당 게시물 불러오기
    public Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // 해당 댓글 불러오기
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // 해당 유저의 팔로우 불러오기
    public UserFollows getFollow(Long follower, Long following) {
        return followRepository.findByFollower_IdAndFollowing_Id(follower, following);
    }
}
