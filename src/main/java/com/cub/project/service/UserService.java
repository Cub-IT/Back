package com.cub.project.service;

import com.cub.project.domain.dto.UserDto;
import com.cub.project.domain.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

import org.springframework.context.MessageSource;
import com.cub.project.repository.UserRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    public User findByName(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

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
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword())).build();
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        User user = getUserById(id);
        //там стоит cascade all, по идее должен удалять все
        userRepository.delete(user);
    }

    public void updateUser(long id, UserDto userDTO) {
        User user = getUserById(id);

        user.setName(userDTO.getName());
        if (Objects.nonNull(userDTO.getPassword()) && userDTO.getPassword().length() > 0) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Login not unique: " + userDTO.getName());
        }
    }

    public void leaveGroup(long userId, long groupId) {

    }
}