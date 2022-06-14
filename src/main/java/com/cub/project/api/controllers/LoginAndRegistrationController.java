package com.cub.project.api.controllers;

import com.cub.project.domain.dto.LoginDto;
import com.cub.project.domain.dto.UserDto;
import com.cub.project.domain.models.User;
import com.cub.project.service.SecurityService;
import com.cub.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginAndRegistrationController {
    private final SecurityService securityService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid LoginDto data) {
        securityService.login(data.getLogin(), data.getPassword());
        return new ResponseEntity<>(userService.getUserByEmail(data.getLogin()), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/reg")
    public ResponseEntity<?> registration(UserDto user) {
        userService.createUser(user);
        securityService.login(user.getEmail(), user.getPassword());
        return ResponseEntity.ok().build();
    }
}
