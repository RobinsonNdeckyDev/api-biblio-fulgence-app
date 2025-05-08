package com.example.fulgence_app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "livre")
@Data
@NoArgsConstructor
public class Livre extends Auditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private Long isbn;

    @Column(nullable = false)
    private String langue;

    @Column(nullable = false)
    private Long nbrePage;

    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    private Auteur auteur;
}
