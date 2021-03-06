package com.cub.project.api.controllers;

import com.cub.project.domain.dto.GroupDto;
import com.cub.project.domain.dto.LoginDto;
import com.cub.project.domain.dto.UserDto;
import com.cub.project.domain.models.Group;
import com.cub.project.domain.models.Participant;
import com.cub.project.domain.models.User;
import com.cub.project.service.SecurityService;
import com.cub.project.service.UserPermissionService;
import com.cub.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserPermissionService permissionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto data) {
        securityService.login(data.getLogin(), data.getPassword());
        log.debug("logged new user: " + data.getLogin());
        return new ResponseEntity<>(UserDto.convert(userService.getUserByEmail(data.getLogin())), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable long userId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isAuthenticated(userId, auth.getUsername())) {
            return new ResponseEntity<>(UserDto.convert(userService.getUserById(userId)), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("{userId}/groups")
    public ResponseEntity<?> getUsersGroups(@PathVariable long userId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isAuthenticated(userId, auth.getUsername())) {
            return new ResponseEntity<>(userService.getUserById(userId).getParticipants().stream()
                    .map(p -> GroupDto.convert(p.getGroup())).collect(Collectors.toList()), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{userId}/edit")
    public ResponseEntity<?> editUser(@RequestBody @Valid UserDto user, @PathVariable long userId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isAuthenticated(userId, auth.getUsername())) {
            userService.updateUser(userId, user);
            return new ResponseEntity<>(UserDto.convert(userService.getUserById(userId)), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("new")
    public ResponseEntity<?> newUser(@RequestBody @Valid UserDto user) {
        userService.createUser(user);
        log.debug("registered new user: " + user.getFirstName() + " " + user.getLastName());
        securityService.login(user.getEmail(), user.getPassword());
        return new ResponseEntity<>(UserDto.convert(userService.getUserByEmail(user.getEmail())), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("{userId}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable long userId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isAuthenticated(userId, auth.getUsername())) {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{userId}/leave/{groupId}")
    public ResponseEntity<?> leaveGroup(@PathVariable long userId, @PathVariable long groupId, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.userIsMember(userId, groupId, auth.getUsername())) {
            userService.leaveGroup(userId, groupId);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("{userId}/join/{code}")
    public ResponseEntity<?> joinGroup(@PathVariable long userId, @PathVariable String code, @AuthenticationPrincipal UserDetails auth) {
        if (permissionService.isAuthenticated(userId, auth.getUsername())){// && !permissionService.isMember(code, auth.getUsername())) {
            userService.joinGroup(userId, code);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
