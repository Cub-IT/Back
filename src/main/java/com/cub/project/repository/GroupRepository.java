package com.cub.project.repository;

import com.cub.project.domain.models.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
