package com.cub.project.repository;

import com.cub.project.domain.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByCode(String code);
}
