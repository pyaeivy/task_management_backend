package org.example.teskmanagementbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String boardTitle;
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "board")
    private List<Task> lists = new ArrayList<>();
    
    public enum Type{
    	PRIVATE,WORKSPACE,PUBLIC, 
    }
}
