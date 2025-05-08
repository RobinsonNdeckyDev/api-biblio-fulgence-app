package com.example.fulgence_app.dtos;

import com.example.fulgence_app.models.Livre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivreResponseDTO {

        private Long id;
        private String nom;
        private String titre;
        private Long isbn;
        private String langue;
        private Long nbrePage;
        private Long auteurId;

        public static LivreResponseDTO fromEntity(Livre livre) {
            LivreResponseDTO dto = new LivreResponseDTO();
            dto.setId(livre.getId());
            dto.setNom(livre.getNom());
            dto.setTitre(livre.getTitre());
            dto.setIsbn(livre.getIsbn());
            dto.setLangue(livre.getLangue());
            dto.setNbrePage(livre.getNbrePage());
            dto.setAuteurId(livre.getAuteur().getId());
            return dto;
        }
}
