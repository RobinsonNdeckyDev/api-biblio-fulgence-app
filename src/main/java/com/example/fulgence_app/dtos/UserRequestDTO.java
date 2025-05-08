package com.example.fulgence_app.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String email;
    private String password;
    private String prenom;
    private String nom;
    private String adresse;
    private String telephone;
    private String description;
    private String photoProfil;
    private String role;
}
