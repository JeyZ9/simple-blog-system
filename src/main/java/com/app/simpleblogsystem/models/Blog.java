package com.app.simpleblogsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
//import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
// ข้อมูล title ต้องมีค่าที่ไม่ซ้ำกัน
@Table(name = "blogs", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String imageUrl;

    private String description;

    @Column(name = "date_time")
    private String dateTime;

    @JsonIgnore
    @NotNull(message = "User cannot be empty")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @NotNull(message = "Category cannot be empty")
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @JsonIgnore
//    @OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL)
//    private Save saves;
    private Set<Save> saves = new HashSet<>();

    public Blog(String title, String imageUrl, String description, User user, Category category, String dateTime) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.user = user;
        this.category = category;
        this.dateTime = dateTime;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
}
