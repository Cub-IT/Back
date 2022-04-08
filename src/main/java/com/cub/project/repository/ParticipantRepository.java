package com.cub.project.repository;

import com.cub.project.domain.models.Participant;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Integer> {
}
