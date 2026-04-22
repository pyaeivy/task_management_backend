package org.example.teskmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;

    @Column(unique = true, nullable = false)
    private String authority;



    @ManyToOne
    private RoleCategory roleCategory;

    public Role(String roleName, String authority) {
        this.roleName = roleName;
        this.authority = authority;
    }


}
