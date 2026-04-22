package org.example.teskmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class RoleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String code;
    private String description;

    @ManyToOne
    private Firm firm;

    @OneToMany(mappedBy = "roleCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles = new ArrayList<>();

    public RoleCategory(String code, String description) {
        this.code = code;
        this.description = description;

    }
}
