package com.cub.project.api.controllers;

import com.cub.project.domain.dto.LoginDto;
import com.cub.project.domain.dto.PostDto;
import com.cub.project.service.PostService;
import com.cub.project.service.UserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/task/")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserPermissionService permissionService;

    @GetMapping("{postId}")
    public ResponseEntity<?> showPost(@PathVariable long postId, Principal auth) {
        if (permissionService.isAllowedToShow(postId, auth.getName())) {
            return new ResponseEntity<>(PostDto.convert(postService.getPostById(postId)), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("new/group/{groupId}")
    public ResponseEntity<?> createPost(@PathVariable long groupId, @RequestBody @Valid PostDto data, Principal auth) {
        if (permissionService.isAllowedToCreate(groupId, auth.getName())) {
            postService.createPost(data, groupId, auth.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{postId}/edit")
    public ResponseEntity<?> editPost(@PathVariable long postId, @RequestBody @Valid PostDto data, Principal auth) {
        if (permissionService.isAllowedToManage(postId, auth.getName())) {
            data.setId(postId);
            postService.updatePost(data);
            return new ResponseEntity<>(PostDto.convert(postService.getPostById(postId)), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("{postId}/delete")
    public ResponseEntity<?> deletePost(@PathVariable long postId, Principal auth) {
        if (permissionService.isAllowedToManage(postId, auth.getName())) {
            postService.deletePost(postId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
