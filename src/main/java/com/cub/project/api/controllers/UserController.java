package com.cub.project.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {
    @PreAuthorize("")
    @GetMapping("{userId}")
    public ResponseEntity<?> getUserDetails() {
        return null;
    }

    @PreAuthorize("")
    @GetMapping("{userId}/groups")
    public ResponseEntity<?> getUsersGroups() {
        return null;
    }

    @PreAuthorize("")
    @PatchMapping("{userId}/edit")
    public ResponseEntity<?> editUser() {
        return null;
    }

    @PreAuthorize("")
    @PostMapping("new")
    public ResponseEntity<?> newUser() {
        return null;
    }

    @PreAuthorize("")
    @DeleteMapping("{userId}/delete")
    public ResponseEntity<?> deleteUser() {
        return null;
    }

    @PreAuthorize("")
    @PatchMapping("{userId}/leave/{groupId}")
    public ResponseEntity<?> leaveGroup() {
        return null;
    }
}
