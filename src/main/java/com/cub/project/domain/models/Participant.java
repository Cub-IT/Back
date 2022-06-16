package com.cub.project.domain.models;

import com.cub.project.domain.converter.DateConverter;
import com.cub.project.domain.converter.RoleConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Participants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User user;

    @Column(name = "role_id", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "assertion_date", nullable = false)
    @Convert(converter = DateConverter.class)
    private LocalDate assertionDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Group group;
}
