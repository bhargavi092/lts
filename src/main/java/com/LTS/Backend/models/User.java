package com.LTS.Backend.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

//    @Column(nullable = false)
    private String role;
    private String name;
    private String mobile;
    @Column(unique = true)
    private String email;
    private String password;
    private int totalLeaveCount;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Leaves> leaves;
    // private leaves
}
