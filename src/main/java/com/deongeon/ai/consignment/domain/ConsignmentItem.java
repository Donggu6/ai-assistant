package com.deongeon.ai.consignment.domain;

import java.time.LocalDateTime;

import com.deongeon.ai.auth.domain.AppUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsignmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;

    private String brand;
    private String modelName;
    private String size;
    private String conditionState;
    private Integer targetPrice;

    @Enumerated(EnumType.STRING)
    private ConsignmentStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
