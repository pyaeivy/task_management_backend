package org.example.teskmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Firm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private FirmType firmType;
    private String firmName;
    private String firmAddress;
    private String firmPhone;
    private String firmEmail;
    private String firmCity;
    private String firmState;
    private String firmZipCode;
    private String firmCountry;

    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoleCategory> roleCategory = new ArrayList<>();

    @ManyToOne
    private User owner;

}
