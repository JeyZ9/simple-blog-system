package com.app.simpleblogsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "saves")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Save {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(mappedBy = "saves", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User users;
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    private User users;

    @ManyToOne
    @JoinColumn(name = "blogs", referencedColumnName = "id", nullable = false)
//    @JoinTable(name = "save_blogs", joinColumns = @JoinColumn(name = "save_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "blog_id", nullable = false))
    private Blog blogs;
//    private Set<Blog> blogs = new HashSet<>();
//    private List<Blog> blogs = new ArrayList<>();

}
