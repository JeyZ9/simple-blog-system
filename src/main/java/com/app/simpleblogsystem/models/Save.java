package com.app.simpleblogsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(mappedBy = "saves")
    private User user;

    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id")
    private Blog blog;
}
