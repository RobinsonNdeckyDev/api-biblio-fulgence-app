package com.example.fulgence_app.controllers;

import com.example.fulgence_app.dtos.LivreRequestDTO;
import com.example.fulgence_app.dtos.LivreResponseDTO;
import com.example.fulgence_app.models.Livre;
import com.example.fulgence_app.services.LivreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    // Liste des livres
    @GetMapping
    public ResponseEntity<List<LivreResponseDTO>> getAllLivres() {
        return ResponseEntity.ok(
                livreService.findAll().stream()
                        .map(LivreResponseDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    // Détails d'un livre par ID
    @GetMapping("/{id}")
    public ResponseEntity<LivreResponseDTO> getLivreById(@PathVariable Long id) {
        return livreService.getLivreById(id)
                .map(LivreResponseDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Enregistrement d'un livre
    @PostMapping("/create-livre")
    public ResponseEntity<LivreResponseDTO> createLivre(@RequestBody LivreRequestDTO livreRequestDTO) {
        try {
            Livre livre = livreService.createLivre(
                    livreRequestDTO.getNom(),
                    livreRequestDTO.getTitre(),
                    livreRequestDTO.getIsbn(),
                    livreRequestDTO.getLangue(),
                    livreRequestDTO.getNbrePage(),
                    livreRequestDTO.getAuteurId()
            );
            return ResponseEntity.ok(LivreResponseDTO.fromEntity(livre));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Mise à jour d'un livre
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLivre(@PathVariable Long id, @RequestBody LivreRequestDTO livreRequestDTO) {
        try {
            return livreService.updateLivre(id, livreRequestDTO)
                    .map(LivreResponseDTO::fromEntity)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Suppression d'un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLivre(@PathVariable Long id) {
        try {
            livreService.deleteLivre(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
