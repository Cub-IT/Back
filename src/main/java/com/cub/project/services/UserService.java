package com.cub.project.services;

import com.cub.project.domain.dto.UpdateUserDTO;
import com.cub.project.domain.dto.UserDto;
import com.cub.project.domain.models.User;
import com.cub.project.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.cub.project.domain.dto.RegistrationUserDTO;

import java.awt.print.Pageable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.context.MessageSource;
import com.cub.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }
        return (UserDetails) user;
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid user id" + id));
    }

    public void createUser(RegistrationUserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .password(passwordEncoder.encode(userDTO.getPassword())).build();
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        User user = getUserById(id);
        //там стоит cascade all, по идее должен удалять все
        userRepository.delete(user);
    }

    public void updateUser(long id, UpdateUserDTO userDTO) {
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