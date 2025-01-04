package com.app.simpleblogsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "username", length = 155, unique = true)
    private String username;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Save> save = new HashSet<>();
//    @JoinColumn(name = "save_id", referencedColumnName = "id")
//    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Save> save = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
//        this.roles = roles;
    }
}
