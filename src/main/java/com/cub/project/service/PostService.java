package com.cub.project.service;

import com.cub.project.domain.dto.PostDto;
import com.cub.project.domain.models.Post;
import com.cub.project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post getPostById(long id) {
        return postRepository.findById((int) id).orElseThrow(() ->
                new IllegalArgumentException("Invalid Post id" + id));
    }

    public void createPost(PostDto postDto) {
        Post post = Post.builder()
                .creationDate(postDto.getCreationDate())
                .editDate(postDto.getEditDate())
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .build();
        postRepository.save(post);
    }

    public void deletePost(long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    public void updatePost(PostDto postDto) {
        Post post = getPostById(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setEditDate(postDto.getEditDate());
    }
}