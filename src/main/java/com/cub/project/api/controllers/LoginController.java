//package com.cub.project.api.controllers;
//
//import com.cub.project.domain.dto.LoginDto;
//import com.cub.project.domain.models.User;
//import com.cub.project.service.SecurityService;
//import com.cub.project.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class LoginController {
//    private final SecurityService securityService;
//    private final UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<User> login(@RequestBody @Valid LoginDto data) {
//        securityService.login(data.getLogin(), data.getPassword());
//        log.debug("logged new user: " + data.getLogin());
//        return new ResponseEntity<>(userService.getUserByEmail(data.getLogin()), new HttpHeaders(), HttpStatus.OK);
//    }
//}
