package com.cub.project.domain.models;

import com.cub.project.domain.converter.DateConverter;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ContentDisposition;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Posts")
@Data
@Builder
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

    @Column(name="description")
    private String description;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User creator;

    @ManyToOne
    private Group group;


}
