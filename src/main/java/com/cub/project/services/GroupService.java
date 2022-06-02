package com.cub.project.services;

import com.cub.project.domain.dto.GroupDto;
import com.cub.project.domain.dto.RegistrationUserDTO;
import com.cub.project.domain.dto.UpdateUserDTO;
import com.cub.project.domain.models.Group;
import com.cub.project.domain.models.User;
import com.cub.project.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Group getGroupById(long id) {
        return groupRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid user id" + id));
    }

    public void createGroup(GroupDto groupDto) {
        Group group = Group.builder()
                .name(groupDto.getName())
                .code(groupDto.getCode())
                .creationDate(groupDto.getCreationDate())
                .build();
        groupRepository.save(group);
    }

    public void deleteGroup(long id) {
        Group group = getGroupById(id);
        groupRepository.delete(group);
    }

    public void updateGroup(GroupDto groupDto) {
        Group group = getGroupById(groupDto.getId());
        group.setName(groupDto.getName());
        group.setCode(groupDto.getCode());
        groupRepository.save(group);
    }

    public void removePost(long groupId, long postId) {
        Group group = getGroupById(groupId);
        group.getPosts().removeIf((post) -> post.getId() == postId);
        groupRepository.save(group);
    }

    public void removeParticipant(long groupId, long userId) {
        Group group = getGroupById(groupId);
        group.getParticipants().removeIf((user) -> user.getId() == userId);
        groupRepository.save(group);
    }
}
