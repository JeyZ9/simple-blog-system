package com.app.simpleblogsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "saves")
//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Save {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User users;

    @ManyToMany
    @JoinTable(
            name = "save_blogs",
            joinColumns = @JoinColumn(name = "save_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "blog_id", nullable = false)
    )
    private Set<Blog> blogs = new HashSet<>();

    public Save(User user){
        this.users = user;
    }

}
