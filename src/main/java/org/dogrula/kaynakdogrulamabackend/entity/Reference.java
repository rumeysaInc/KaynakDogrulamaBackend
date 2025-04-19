package org.dogrula.kaynakdogrulamabackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reference_data")
public class Reference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private String sourceType; // SCIENTIFIC, NEWS, BLOG vb.

    private boolean verified;
    private LocalDateTime createdAt = LocalDateTime.now();
}
