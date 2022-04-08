package com.cub.project.repository;

import com.cub.project.domain.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
