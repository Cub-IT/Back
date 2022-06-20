package com.cub.project.api.controllers;

import com.cub.project.domain.dto.GroupDto;
import com.cub.project.domain.dto.PostDto;
import com.cub.project.domain.dto.UserDto;
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

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/group/")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final UserPermissionService permissionService;

    @GetMapping("{groupId}")
    public ResponseEntity<?> getGroupData(@PathVariable long groupId, Principal auth) {
        if (permissionService.isMember(groupId, auth.getName())) {
            return new ResponseEntity<>(GroupDto.convert(groupService.getGroupById(groupId)), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("new")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto group, Principal auth) {
        groupService.createGroup(group, auth.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("{groupId}/posts")
    public ResponseEntity<?> getPosts(@PathVariable long groupId, Principal auth) {
        if (permissionService.isMember(groupId, auth.getName())) {
            return new ResponseEntity<>(groupService.getGroupById(groupId).getPosts().stream().map(PostDto::convert), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("{groupId}/participants")
    public ResponseEntity<?> getParticipants(@PathVariable long groupId, Principal auth) {
        if (permissionService.isMember(groupId, auth.getName())) {
            return new ResponseEntity<>(groupService.getGroupById(groupId).getParticipants().stream().map(p -> UserDto.convert(p.getUser())), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{groupId}/remove/participant/{participantId}")
    public ResponseEntity<?> removeParticipant(@PathVariable long groupId, @PathVariable long userId, Principal auth) {
        if (permissionService.isAdmin(groupId, auth.getName())) {
            groupService.removeParticipant(groupId, userId);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{groupId}/remove/post/{postId}")
    public ResponseEntity<?> removePost(@PathVariable long groupId, @PathVariable long postId, Principal auth) {
        if (permissionService.isAdmin(groupId, auth.getName())) {
            groupService.removePost(groupId, postId);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{groupId}/edit")
    public ResponseEntity<?> editGroup(@PathVariable long groupId, @RequestBody GroupDto group, Principal auth) {
        if (permissionService.isAdmin(groupId, auth.getName())) {
            group.setId(groupId);
            groupService.updateGroup(group);
            return new ResponseEntity<>(GroupDto.convert(groupService.getGroupById(groupId)), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("{groupId}/delete")
    public ResponseEntity<?> deleteGroup(@PathVariable long groupId, Principal auth) {
        if (permissionService.isAdmin(groupId, auth.getName())) {
            groupService.deleteGroup(groupId);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
