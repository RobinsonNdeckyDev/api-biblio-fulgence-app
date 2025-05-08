package com.example.fulgence_app.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuteurResponseDTO {
    private Long id;
    private Long userId;
    private String prenom;
    private String nom;
    private String email;
}
