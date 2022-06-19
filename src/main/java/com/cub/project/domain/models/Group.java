package com.cub.project.domain.models;

import com.cub.project.domain.converter.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`Groups`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name = "`code`", nullable = false)
    private String code;

    @Column(name="group_color")
    private String color;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    transient private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    transient private List<Post> posts = new ArrayList<>();

    @Column(name = "creation_date")
    @Convert(converter = DateConverter.class)
    private LocalDate creationDate;
}
