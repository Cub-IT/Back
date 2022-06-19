package com.cub.project.api.controllers;

import com.cub.project.domain.dto.GroupDto;
import com.cub.project.domain.models.Group;
import com.cub.project.domain.models.Post;
import com.cub.project.domain.models.User;
import com.cub.project.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/group/")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PreAuthorize("")
    @GetMapping("{groupId}")
    public ResponseEntity<Group> getGroupData(@PathVariable long groupId, @AuthenticationPrincipal User auth) {
        return new ResponseEntity<>(groupService.getGroupById(groupId), new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("")
    @PostMapping("new")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto group, @AuthenticationPrincipal User auth) {
        groupService.createGroup(group);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("")
    @GetMapping("{groupId}/posts")
    public ResponseEntity<Collection<Post>> getPosts(@PathVariable long groupId, @AuthenticationPrincipal User auth) {
        return new ResponseEntity<>(groupService.getGroupById(groupId).getPosts(), new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("")
    @PatchMapping("{groupId}/remove/participant/{participantId}")
    public ResponseEntity<?> removeParticipant(@PathVariable long groupId, @PathVariable long userId, @AuthenticationPrincipal User auth) {
        groupService.removeParticipant(groupId, userId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("")
    @PatchMapping("{groupId}/remove/post/{postId}")
    public ResponseEntity<?> removePost(@PathVariable long groupId, @PathVariable long postId, @AuthenticationPrincipal User auth) {
        groupService.removePost(groupId, postId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("")
    @PatchMapping("{groupId}/edit")
    public ResponseEntity<?> editGroup(@PathVariable long groupId, @RequestBody GroupDto group, @AuthenticationPrincipal User auth) {
        groupService.updateGroup(group);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("")
    @DeleteMapping("{groupId}/delete")
    public ResponseEntity<?> deleteGroup(@PathVariable long groupId, @AuthenticationPrincipal User auth) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().build();
    }
}
