// AuteurController.java
    package com.example.fulgence_app.controllers;

    import com.example.fulgence_app.dtos.AuteurRequestDTO;
    import com.example.fulgence_app.models.Auteur;
    import com.example.fulgence_app.models.User;
    import com.example.fulgence_app.services.AuteurService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/api/auteurs")
    public class AuteurController {

        private final AuteurService auteurService;

        public AuteurController(AuteurService auteurService) {
            this.auteurService = auteurService;
        }

        // Récupérer tous les auteurs
        @GetMapping
        public ResponseEntity<List<Auteur>> getAllAuteurs() {
            return ResponseEntity.ok(auteurService.findAll());
        }

        // Récupérer un auteur par son ID
        @GetMapping("/{id}")
        public ResponseEntity<Auteur> getAuteurById(@PathVariable Long id) {
            return auteurService.getAuteurById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        // Créer un nouvel auteur
        // AuteurController.java
        @PostMapping
        public ResponseEntity<?> createAuteur(@RequestBody AuteurRequestDTO auteurRequestDTO) {
            try {
                Auteur auteur = auteurService.createAuteur(auteurRequestDTO);
                return ResponseEntity.ok(auteur);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(Map.of("error", "Erreur serveur"));
            }
        }

        // Mettre à jour un auteur
        @PutMapping("/{id}")
        public ResponseEntity<?> updateAuteur(@PathVariable Long id, @RequestBody AuteurRequestDTO auteurRequestDTO) {
            try {
                return auteurService.updateAuteur(id, auteurRequestDTO)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(Map.of("error", "Erreur serveur"));
            }
        }

        // Supprimer un auteur
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteAuteur(@PathVariable Long id) {
            try {
                auteurService.deleteAuteur(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
    }