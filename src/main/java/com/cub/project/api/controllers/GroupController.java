package com.cub.project.api.controllers;

import com.cub.project.domain.dto.GroupDto;
import com.cub.project.domain.models.Group;
import com.cub.project.domain.models.Post;
import com.cub.project.domain.models.User;
import com.cub.project.service.GroupService;
import com.cub.project.service.UserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/group/")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final UserPermissionService permissionService;

    @GetMapping("{groupId}")
    public ResponseEntity<?> getGroupData(@PathVariable long groupId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isMember(groupId, auth.getUsername())) {
            return new ResponseEntity<>(GroupDto.convert(groupService.getGroupById(groupId)), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("new")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto group, @AuthenticationPrincipal UserDetails auth) {
        groupService.createGroup(group, auth.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("{groupId}/posts")
    public ResponseEntity<?> getPosts(@PathVariable long groupId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isMember(groupId, auth.getUsername())) {
            return new ResponseEntity<>(groupService.getGroupById(groupId).getPosts(), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{groupId}/remove/participant/{participantId}")
    public ResponseEntity<?> removeParticipant(@PathVariable long groupId, @PathVariable long userId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isAdmin(groupId, auth.getUsername())) {
            groupService.removeParticipant(groupId, userId);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{groupId}/remove/post/{postId}")
    public ResponseEntity<?> removePost(@PathVariable long groupId, @PathVariable long postId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isAdmin(groupId, auth.getUsername())) {
            groupService.removePost(groupId, postId);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{groupId}/edit")
    public ResponseEntity<?> editGroup(@PathVariable long groupId, @RequestBody GroupDto group, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isAdmin(groupId, auth.getUsername())) {
            groupService.updateGroup(group);
            return new ResponseEntity<>(GroupDto.convert(groupService.getGroupById(groupId)), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("{groupId}/delete")
    public ResponseEntity<?> deleteGroup(@PathVariable long groupId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isAdmin(groupId, auth.getUsername())) {
            groupService.deleteGroup(groupId);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
