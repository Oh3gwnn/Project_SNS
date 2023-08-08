package com.be05.sns.service;

import com.be05.sns.dto.CommentDto;
import com.be05.sns.entity.Article;
import com.be05.sns.entity.Comment;
import com.be05.sns.entity.Users;
import com.be05.sns.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final GetObjService getObj;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    // 댓글 생성
    public void create(Long articleId, CommentDto dto,
                       Authentication authentication) {
        Users user = getObj.getUser(authentication.getName());
        Article article = getObj.getArticle(articleId);
        Comment comment = modelMapper.map(dto.newComment(user, article), Comment.class);
        commentRepository.save(comment);
    }

    // 댓글 업데이트
    public void update(Long articleId, Long commentId, CommentDto dto,
                       Authentication authentication) {
        getObj.getArticle(articleId); // 사용만 해도 해당 feed 유무 판단 가능
        Users user = getObj.getUser(authentication.getName());
        Comment comment = getObj.getComment(commentId);
        validatePassword(user, comment);

        comment.setContent(dto.getContent());
        commentRepository.save(comment);
    }

    // 댓글 삭제
    public void delete(Long articleId, Long commentId,
                       Authentication authentication) {
        getObj.getArticle(articleId);
        Users user = getObj.getUser(authentication.getName());
        Comment comment = getObj.getComment(commentId);
        validatePassword(user, comment);

        // commentRepository.delete(comment) 대신 작성함.
        comment.setContent("[삭제된 댓글입니다.]");
        commentRepository.save(comment);
    }

    // 비밀번호 검증
    private void validatePassword(Users user, Comment comment) {
        if (!user.getPassword().equals(comment.getUserId().getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
