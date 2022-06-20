package com.cub.project.service;

import com.cub.project.domain.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPermissionService {
    private final UserService userService;
    private final GroupService groupService;

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

    public boolean isAllowedToShow(long postId, String authUserLogin) {
        return userService.getUserByEmail(authUserLogin).getParticipants().stream()
                .map(Participant::getGroup).anyMatch(g -> g.getPosts().stream().anyMatch(p -> p.getId() == postId));
    }

    public boolean isAllowedToManage(long postId, String authUserLogin) {
        return userService.getUserByEmail(authUserLogin).getPosts().stream().anyMatch(p -> p.getId() == postId);
    }

    public boolean isAllowedToCreate(long groupId, String authUserLogin) {
        return groupService.getGroupById(groupId).getParticipants().stream()
                .anyMatch(p -> p.getUser() == userService.getUserByEmail(authUserLogin)
                        && p.getRole() == Role.ADMIN || p.getRole() == Role.OWNER);
    }
}
