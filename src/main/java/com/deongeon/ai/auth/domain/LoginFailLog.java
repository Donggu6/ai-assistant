package com.deongeon.ai.auth.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class LoginFailLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String email;
    private String ip;
    private String userAgent;

    private LocalDateTime createdAt = LocalDateTime.now();
}
