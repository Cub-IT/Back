package com.cub.project.repository;

import com.cub.project.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByName(String name);
    User findByEmail(String email);
}
