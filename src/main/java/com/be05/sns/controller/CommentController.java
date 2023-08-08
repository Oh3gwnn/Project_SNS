package com.be05.sns.controller;

import com.be05.sns.dto.comment.CommentDto;
import com.be05.sns.dto.ResponseDto;
import com.be05.sns.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed/read/{articleId}")
public class CommentController {
    private final ResponseDto response;
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseDto createComment(@PathVariable("articleId") Long articleId,
                                     @RequestBody CommentDto dto,
                                     Authentication authentication) {
        commentService.create(articleId, dto, authentication);
        return response.toMessage("댓글이 작성되었습니다.");
    }

    @PutMapping("/comment/{commentId}")
    public ResponseDto updateComment(@PathVariable("articleId") Long articleId,
                                     @PathVariable("commentId") Long commentId,
                                     @RequestBody CommentDto dto,
                                     Authentication authentication) {
        commentService.update(articleId, commentId, dto, authentication);
        return response.toMessage("댓글이 수정되었습니다.");
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseDto deleteComment(@PathVariable("articleId") Long articleId,
                                     @PathVariable("commentId") Long commentId,
                                     Authentication authentication) {
        commentService.delete(articleId, commentId, authentication);
        return response.toMessage("댓글이 삭제되었습니다.");
    }
}
