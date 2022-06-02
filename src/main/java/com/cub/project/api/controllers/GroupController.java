package com.cub.project.api.controllers;

import com.cub.project.domain.dto.GroupDto;
import com.cub.project.domain.dto.UserDto;
import com.cub.project.domain.models.Group;
import com.cub.project.domain.models.User;
import com.cub.project.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/group/")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PreAuthorize("")
    @GetMapping("{groupId}")
    public ResponseEntity<?> getGroupData(@PathVariable long groupId) {
        return new ResponseEntity<>(groupService.getGroupById(groupId), new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("")
    @PostMapping("new")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto group) {
        groupService.createGroup(group);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("")
    @GetMapping("{groupId}/posts")
    public ResponseEntity<?> getPosts(@PathVariable long groupId) {
        return new ResponseEntity<>(groupService.getGroupById(groupId).getPosts(), new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("")
    @PatchMapping("{groupId}/remove/participant/{participantId}")
    public ResponseEntity<?> removeParticipant(@PathVariable long groupId, @PathVariable long userId) {
        groupService.removeParticipant(groupId, userId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("")
    @PatchMapping("{groupId}/remove/post/{postId}")
    public ResponseEntity<?> removePost(@PathVariable long groupId, @PathVariable long postId) {
        groupService.removePost(groupId, postId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("")
    @PatchMapping("{groupId}/edit")
    public ResponseEntity<?> editGroup(@PathVariable long groupId, @RequestBody GroupDto group) {
        groupService.updateGroup(group);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("")
    @DeleteMapping("{groupId}/delete")
    public ResponseEntity<?> deleteGroup(@PathVariable long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().build();
    }
}
