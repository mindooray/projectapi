package com.nhnacademy.team4.projectapi.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostDTO {
    private String title;
    private String content;
    private Long accountId;
    private Long commentId;

}
