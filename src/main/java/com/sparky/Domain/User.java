package com.sparky.Domain;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "user")
    private List<UserLimit> limits;

    @OneToMany(mappedBy = "user")
    private List<RequestLog> requests;

    public enum Role {
        ROLE_SPARKY_ADMIN,
        ROLE_COMPANY_ADMIN,
        ROLE_USER
    }
}