package com.cub.project.service;

import com.cub.project.domain.dto.UserDto;
import com.cub.project.domain.models.Group;
import com.cub.project.domain.models.Participant;
import com.cub.project.domain.models.Role;
import com.cub.project.domain.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.context.MessageSource;
import com.cub.project.repository.UserRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

//    public User findByName(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return user;
//    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid user id" + id));
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return user;
    }

    public void createUser(UserDto userDTO) {
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .color(userDTO.getColor()).build();
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        User user = getUserById(id);
        //там стоит cascade all, по идее должен удалять все
        userRepository.delete(user);
    }

    public void updateUser(long id, UserDto userDTO) {
        User user = getUserById(id);

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getPassword() != null && userDTO.getPassword().length() > 0) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getColor() != null && !userDTO.getColor().equals("")) {
            user.setColor(userDTO.getColor());
        }

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Login not unique: " + userDTO.getEmail());
        }
    }

    public void leaveGroup(long userId, long groupId) {
        User user = getUserById(userId);
        user.getParticipants().removeIf((group) -> group.getId() == groupId);
        userRepository.save(user);
    }

    public void joinGroup(long userId, String code) {
        User user = getUserById(userId);
        Group group = groupService.getGroupByCode(code);

        Participant participant = Participant.builder()
                .user(user)
                .group(group)
                .assertionDate(LocalDate.now())
                .role(Role.MEMBER).build();

        user.addParticipant(participant);
        group.addParticipant(participant);

        userRepository.save(user);
        groupService.save(group);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}