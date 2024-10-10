package org.acme;

import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

    private String text;

    private Long userId;

    private Long blogId;

}
