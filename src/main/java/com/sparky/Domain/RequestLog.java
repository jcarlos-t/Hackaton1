package com.sparky.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String query;

    @Column(length = 5000)
    private String response;

    private int tokensUsed;
    private LocalDateTime timestamp;
    private String modelUsed;
    private String filename;

    @ManyToOne
    private User user;
}
