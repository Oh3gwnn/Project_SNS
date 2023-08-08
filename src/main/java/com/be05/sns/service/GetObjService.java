package com.be05.sns.service;

import com.be05.sns.entity.*;
import com.be05.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetObjService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;
    private final FriendRepository friendRepository;

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

    // 해당 유저 팔로우 불러오기(1:1)
    public UserFollows getFollow(Long follower, Long following) {
        return followRepository.findByFollower_IdAndFollowing_Id(follower, following);
    }

    // 해당 유저 전체 팔로우 불러오기
    public List<UserFollows> getFollow(Long userId) {
        return followRepository.findByFollower_Id(userId);
    }

    // 해당 유저의 친구 신청 불러오기(1:1)
    public UserFriends getFriend(Long fromUser, Long toUser) {
        return friendRepository.findByFromUser_IdAndToUser_Id(fromUser, toUser);
    }

    // 해당 유저의 전체 친구 불러오기
    public List<UserFriends> getFriend(Long userId) {
        return friendRepository.findByToUser_IdAndStatus(userId, "친구");
    }


}
