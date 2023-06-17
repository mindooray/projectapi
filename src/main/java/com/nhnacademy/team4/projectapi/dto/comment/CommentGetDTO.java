package com.nhnacademy.team4.projectapi.dto.comment;

import com.nhnacademy.team4.projectapi.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CommentGetDTO {
    private Long commentId;
    private Long accountId;
    private String content;
    private LocalDateTime createTime;

    public static CommentGetDTO toDto(Comment comment) {
        CommentGetDTO dto = new CommentGetDTO();
        dto.commentId = comment.getCommentId();
        dto.accountId = comment.getAccountId();
        dto.content = comment.getContent();
        dto.createTime = comment.getCreateDate();

        return dto;
    }
}
