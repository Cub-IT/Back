package com.cub.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPermissionService {
    private UserService userService;

    public boolean isAllowedToGetDetails(long id) {
        return false;
    }
}
