package com.cub.project.domain.models;

import com.cub.project.domain.converter.DateConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long id;
    @Column(name = "creation_date")
    @Convert(converter = DateConverter.class)
    private LocalDate creationDate;
    @Column(name = "edit_date")
    @Convert(converter = DateConverter.class)
    private LocalDate editDate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User creator;
    @ManyToOne
    private Group group;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
}
