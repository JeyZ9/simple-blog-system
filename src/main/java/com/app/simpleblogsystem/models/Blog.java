package com.app.simpleblogsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
//import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "blogs")
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String imageUrl;

    private String description;

    @Column(name = "date_time")
    private Date dateTime;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Categories categories;

    @OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();

    public Blog(String title, String imageUrl, String description, User user, Categories categories, Set<Comment> comments, Set<Like> likes) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.dateTime = new Date();
        this.user = user;
        this.categories = categories;
        this.comments = comments;
        this.likes = likes;
    }
}
