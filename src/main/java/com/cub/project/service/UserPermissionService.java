package com.cub.project.service;

import com.cub.project.domain.models.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPermissionService {
    private UserService userService;
    private GroupService groupService;

    public boolean isAuthenticated(long userId, String authUserLogin) {
        if (userService.getUserById(userId) == null) {
            return false;
        }
        return userService.getUserById(userId).equals(userService.getUserByEmail(authUserLogin));
    }

    public boolean isMember(long groupId, String authUserLogin) {
        return userService.getUserByEmail(authUserLogin).getParticipants().stream().anyMatch(p -> p.getGroup().getId() == groupId);
    }

    public boolean userIsMember(long userId, long groupId, String authUserLogin) {
        if (isAuthenticated(userId, authUserLogin)) {
            return isMember(groupId, authUserLogin);
        }
        return false;
    }

    public boolean isAdmin(long groupId, String authUserLogin) {
        Group group = groupService.getGroupById(groupId);
        return group != null && group.getParticipants().stream().anyMatch(p -> p.getUser().getEmail().equals(authUserLogin));
    }
}
