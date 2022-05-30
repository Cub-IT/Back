package com.cub.project.services;

import com.cub.project.domain.dto.UpdateUserDTO;
import com.cub.project.domain.dto.UserDto;
import com.cub.project.domain.models.User;
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
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
public UserService(UserRepository userRepository, MessageSource messageSource, PasswordEncoder passwordEncoder){
    this.userRepository=userRepository;
    this.messageSource=messageSource;
    this.passwordEncoder=passwordEncoder;
}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByUsername(username);
   if(Objects.isNull(user)){
       throw new UsernameNotFoundException(username);
   }
   return (UserDetails) user;
    }
public User getUserById(long id){
    //Сань, глянь чё за рофл, должно работать же
    return userRepository.findById(id).orElseThrow() ->
    new IllegalArgumentException("Invalid user id" + id);
}
public void createUser(RegistrationUserDTO userDTO){
    User user= User.builder()
            .name(userDTO.getName())
            .password(passwordEncoder.encode(userDTO.getPassword));
}

public void deleteUser(long id){
    User user=getUserById(id);
//По идее, тут мы ещё удаляем с каждой группы этот аккаунт
    userRepository.delete(user);
}
public void updateUser(long id, UpdateUserDTO userDTO){
    User user=getUserById(id);

    user.setName(userDTO.getName());
    if(Objects.nonNull(userDTO.getPassword()) && userDTO.getPassword().length()>0) {
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }
        //Что-то с ролью надо придумать

        try{
            userRepository.save(user);
        }
        catch(DataIntegrityViolationException e){
            log.error("Login not unique: " +userDTO.getName());
            throw new UsernameNotUniqueException(messageSource.getMessage("users.registration.login.not_unique",
                    null,
                    LocaleContextHolder.getLocale())+userDTO.getName(),e);
        }
    }
}


