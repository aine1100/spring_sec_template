package com.backend.irire.Model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    private String token;
    @Column(nullable = false)
    private Instant expires;
    private boolean revoked;
}
