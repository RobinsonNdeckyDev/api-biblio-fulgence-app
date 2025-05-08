// AuteurRequestDTO.java
package com.example.fulgence_app.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuteurRequestDTO {
    private String prenom;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
    private String photoProfil;
    private String description;
    private String password;
}