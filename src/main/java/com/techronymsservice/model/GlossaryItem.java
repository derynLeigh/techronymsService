package com.techronymsservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor

public class GlossaryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String definition;
    private String furtherReading;

    @CreationTimestamp
    private Instant created;

    @UpdateTimestamp
    private Instant lastUpdated;

    public GlossaryItem(String title, String definition) {
        this.title = title;
        this.definition = definition;
    }
}

