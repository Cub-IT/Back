package com.cub.project.api.controllers;

import com.cub.project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task/")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("{taskId}")
    public ResponseEntity<?> showPost() {
        return null;
    }

    @PostMapping("new")
    public ResponseEntity<?> createPost() {
        return null;
    }

    @PatchMapping("{taskId}/edit")
    public ResponseEntity<?> editPost() {
        return null;
    }

    @DeleteMapping("{taskId}/delete")
    public ResponseEntity<?> deletePost() {
        return null;
    }
}
