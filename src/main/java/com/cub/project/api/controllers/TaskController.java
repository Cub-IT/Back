package com.cub.project.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task/")
@RequiredArgsConstructor
public class TaskController {

    @PreAuthorize("")
    @GetMapping("{taskId}")
    public ResponseEntity<?> showTask() {
        return null;
    }

    @PreAuthorize("")
    @PostMapping("new")
    public ResponseEntity<?> createTask() {
        return null;
    }

    @PreAuthorize("")
    @PatchMapping("{taskId}/edit")
    public ResponseEntity<?> editTask() {
        return null;
    }

    @PreAuthorize("")
    @DeleteMapping("{taskId}/delete")
    public ResponseEntity<?> deleteTask() {
        return null;
    }
}
