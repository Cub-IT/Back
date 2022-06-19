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

    public boolean userIsMember(long userId, long groupId, String authUserLogin) {
        if (isAuthenticated(userId, authUserLogin)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMember(long groupId, String authUserLogin) {
        return false;
    }

    public boolean isAdmin(long groupId, String authUserLogin) {
        return false;
    }
}
