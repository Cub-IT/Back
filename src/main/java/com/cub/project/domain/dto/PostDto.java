package com.cub.project.domain.dto;

import com.cub.project.domain.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;
    private LocalDate creationDate;
    private LocalDate editDate;
    private String description;

    public static final PostDto convert(Post post) {
        return new PostDto(post.getId(), post.getCreationDate(), post.getEditDate(), post.getDescription());
    }
}
