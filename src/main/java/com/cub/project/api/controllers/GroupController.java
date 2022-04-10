package com.cub.project.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/group/")
@RequiredArgsConstructor
public class GroupController {
    @PreAuthorize("")
    @GetMapping("{groupId}")
    public ResponseEntity<?> getGroupData() {
        return null;
    }

    @PreAuthorize("")
    @PostMapping("new")
    public ResponseEntity<?> createGroup() {
        return null;
    }

    @PreAuthorize("")
    @GetMapping("{groupId}/posts")
    public ResponseEntity<?> getPosts() {
        return null;
    }

    @PreAuthorize("")
    @PatchMapping("{groupId}/remove/participant/{participantId}")
    public ResponseEntity<?> removeParticipant() {
        return null;
    }

    @PreAuthorize("")
    @PatchMapping("{groupId}/remove/post/{postId}")
    public ResponseEntity<?> removePost() {
        return null;
    }

    @PreAuthorize("")
    @PatchMapping("{groupId}/edit")
    public ResponseEntity<?> editGroup() {
        return null;
    }

    @PreAuthorize("")
    @DeleteMapping("{groupId}/delete")
    public ResponseEntity<?> deleteGroup() {
        return null;
    }
}
