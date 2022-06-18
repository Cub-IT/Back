package com.cub.project.service;

import com.cub.project.domain.dto.GroupDto;
import com.cub.project.domain.models.Group;
import com.cub.project.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Log4j2
@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Group getGroupById(long id) {
        return groupRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid group id" + id));
    }

    public void createGroup(GroupDto groupDto) {
        Group group = Group.builder()
                .title(groupDto.getTitle())
                .description(groupDto.getDescription())
                .code(groupDto.getCode())
                .creationDate(LocalDate.now())
                .build();
        groupRepository.save(group);
    }

    public void deleteGroup(long id) {
        Group group = getGroupById(id);
        groupRepository.delete(group);
    }

    public void updateGroup(GroupDto groupDto) {
        Group group = getGroupById(groupDto.getId());
        group.setTitle(groupDto.getTitle());
        group.setDescription(groupDto.getDescription());
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
