package com.cub.project.service;

import com.cub.project.Constants;
import com.cub.project.domain.dto.GroupDto;
import com.cub.project.domain.models.Group;
import com.cub.project.domain.models.Participant;
import com.cub.project.domain.models.Role;
import com.cub.project.domain.models.User;
import com.cub.project.repository.GroupRepository;
import com.cub.project.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Log4j2
@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final RandomStringGenerator codeGenerator;
    private final UserService userService;
    private final ParticipantRepository participantRepository;

    public Group getGroupById(long id) {
        return groupRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid group id" + id));
    }

    public Group getGroupByCode(String code) {
        Group group = groupRepository.findByCode(code);
        if (group == null) {
            throw new IllegalArgumentException("Invalid group code" + code);
        }
        return groupRepository.findByCode(code);
    }

    public void createGroup(GroupDto groupDto, String authUserLogin) {
        String code = codeGenerator.getString(Constants.codeLength);
        while (groupRepository.existsByCode(code)) {
            code = codeGenerator.getString(Constants.codeLength);
        }
        String[] colors = {"#161725", "#3897832", "#3320945", "#5664378", "#14228064", "#153498"};
        int rnd = new Random().nextInt(colors.length);
        User creator = userService.getUserByEmail(authUserLogin);
        Group group = Group.builder()
                .title(groupDto.getTitle())
                .description(groupDto.getDescription())
                .code(code)
                .creationDate(LocalDate.now())
                .color(colors[rnd]).build();
        Participant participant = Participant.builder()
                .user(creator)
                .group(group)
                .assertionDate(LocalDate.now())
                .role(Role.OWNER).build();
        group.addParticipant(participant);
        creator.addParticipant(participant);
        groupRepository.save(group);
        userService.save(creator);
        //participantRepository.save(participant);
    }

    public void deleteGroup(long id) {
        Group group = getGroupById(id);
        groupRepository.delete(group);
    }

    public void updateGroup(GroupDto groupDto) {
        Group group = getGroupById(groupDto.getId());
        group.setTitle(groupDto.getTitle());
        group.setDescription(groupDto.getDescription());
        //group.setCode(groupDto.getCode());
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

    public void save(Group group) {
        groupRepository.save(group);
    }
}
