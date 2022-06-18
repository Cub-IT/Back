package com.cub.project.api.controllers;

import com.cub.project.domain.dto.UserDto;
import com.cub.project.domain.models.Group;
import com.cub.project.domain.models.Participant;
import com.cub.project.domain.models.User;
import com.cub.project.service.SecurityService;
import com.cub.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final SecurityService securityService;
    private final UserService userService;

    @PreAuthorize("")
    @GetMapping("{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("")
    @GetMapping("{userId}/groups")
    public ResponseEntity<Collection<Group>> getUsersGroups(@PathVariable long userId) {
        return new ResponseEntity<>(userService.getUserById(userId).getParticipants().stream()
                .map(Participant::getGroup).collect(Collectors.toList()), new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("")
    @PatchMapping("{userId}/edit")
    public ResponseEntity<?> editUser(@RequestBody @Valid UserDto user, @PathVariable long userId) {
        userService.updateUser(userId, user);
        return new ResponseEntity<>(userService.getUserByEmail(user.getEmail()), new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("")
    @PostMapping("new")
    public ResponseEntity<?> newUser(@RequestBody @Valid UserDto user) {
        userService.createUser(user);
        log.debug("registered new user: " + user.getFirstName() + " " + user.getLastName());
        securityService.login(user.getEmail(), user.getPassword());
        return new ResponseEntity<>(userService.getUserByEmail(user.getEmail()), new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("")
    @DeleteMapping("{userId}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("")
    @PatchMapping("{userId}/leave/{groupId}")
    public ResponseEntity<?> leaveGroup(@PathVariable long userId, @PathVariable long groupId) {
        userService.leaveGroup(userId, groupId);
        return ResponseEntity.ok().build();
    }
}
