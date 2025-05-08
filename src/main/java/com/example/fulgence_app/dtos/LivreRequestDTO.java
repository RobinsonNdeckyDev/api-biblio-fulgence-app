package com.example.fulgence_app.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivreRequestDTO {
    private String nom;
    private String titre;
    private Long isbn;
    private String langue;
    private Long nbrePage;
    private Long auteurId;
}
