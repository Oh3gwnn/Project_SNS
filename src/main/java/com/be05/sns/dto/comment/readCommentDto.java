package com.be05.sns.dto.comment;

import com.be05.sns.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class readCommentDto {
    private String userName;
    private String content;
    private String createdAt;

    public static readCommentDto fromComment(Comment comment) {
        return readCommentDto.builder()
                .userName(comment.getUserId().getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("MM월 dd일(E) a HH시 mm분")))
                .build();
    }
}
