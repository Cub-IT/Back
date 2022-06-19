package com.cub.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPermissionService {
    private UserService userService;

    public boolean isAuthenticated(long userId, String authUserLogin) {
        return false;
    }

    public boolean isMember(long userId, long groupId, String authUserLogin) {
        return false;
    }
}
