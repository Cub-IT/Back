package com.cub.project.domain.models;

import com.cub.project.domain.converter.DateConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Participant> participants;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;
    @Column(name = "creation_date")
    @Convert(converter = DateConverter.class)
    private LocalDate creationDate;
}
