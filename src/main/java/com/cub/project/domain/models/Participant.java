package com.cub.project.domain.models;

import com.cub.project.domain.converter.DateConverter;
import com.cub.project.domain.converter.RoleConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User user;
    @Column(name = "role_id", nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;
    @Column(name = "assertion_date", nullable = false)
    @Convert(converter = DateConverter.class)
    private LocalDate assertionDate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Group group;
}
