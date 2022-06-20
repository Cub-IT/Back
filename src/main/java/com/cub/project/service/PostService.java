package com.cub.project.service;

import com.cub.project.domain.dto.PostDto;
import com.cub.project.domain.models.Post;
import com.cub.project.repository.GroupRepository;
import com.cub.project.repository.PostRepository;
import com.cub.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public Post getPostById(long id) {
        return postRepository.findById((int) id).orElseThrow(() ->
                new IllegalArgumentException("Invalid Post id" + id));
    }

    public void createPost(PostDto postDto, long groupId, String authUserLogin) {
        Post post = Post.builder()
                .creationDate(LocalDate.now())
                .editDate(LocalDate.now())
                .description(postDto.getDescription())
                .creator(userRepository.findByEmail(authUserLogin))
                .group(groupRepository.getById(groupId)).build();
        postRepository.save(post);
    }

    public void deletePost(long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    public void updatePost(PostDto postDto) {
        Post post = getPostById(postDto.getId());
        post.setDescription(postDto.getDescription());
        post.setEditDate(LocalDate.now());
    }
}
