package org.example.teskmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user", orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    private Firm firm;


    public User(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
