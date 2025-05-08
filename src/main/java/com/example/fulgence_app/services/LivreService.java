package com.example.fulgence_app.services;

import com.example.fulgence_app.dtos.LivreRequestDTO;
import com.example.fulgence_app.models.Auteur;
import com.example.fulgence_app.models.Livre;
import com.example.fulgence_app.repository.AuteurRepository;
import com.example.fulgence_app.repository.LivreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreService {
    private final LivreRepository livreRepository;
    private final AuteurRepository auteurRepository;

    public LivreService(LivreRepository livreRepository, AuteurRepository auteurRepository) {
        this.auteurRepository = auteurRepository;
        this.livreRepository = livreRepository;
    }

    // Récupérer tous les livres
    public List<Livre> findAll() {
        return livreRepository.findAll();
    }

    // Récupérer un livre par son id
    public Optional<Livre> getLivreById(Long id) {
        return livreRepository.findById(id);
    }

    // Créer un Livre
    public Livre createLivre(
            String nom,
            String titre,
            Long isbn,
            String langue,
            Long nbrePage,
            Long auteurId
    ) {
        if (livreRepository.existsByNom(nom)) {
            throw new IllegalArgumentException("Ce livre existe déjà.");
        }

        // On récupère l'auteur via l'ID
        Auteur auteur = auteurRepository.findById(auteurId)
                .orElseThrow(() -> new IllegalArgumentException("Auteur ne correspond pas ou n'existe pas (ID=" + auteurId + ")"));

        Livre livre = new Livre();
        livre.setNom(nom);
        livre.setTitre(titre);
        livre.setIsbn(isbn);
        livre.setLangue(langue);
        livre.setNbrePage(nbrePage);
        livre.setAuteur(auteur);

        return livreRepository.save(livre);
    }

    // Modifier un livre
    public Optional<Livre> updateLivre(Long id, LivreRequestDTO livreRequestDTO) {
        return livreRepository.findById(id).map(livre -> {
            if (!livre.getNom().equals(livreRequestDTO.getNom())
                    && livreRepository.existsByNom(livreRequestDTO.getNom())) {
                throw new IllegalArgumentException("Ce nom est déjà utilisé par un autre livre.");
            }

            livre.setNom(livreRequestDTO.getNom());
            livre.setTitre(livreRequestDTO.getTitre());
            livre.setLangue(livreRequestDTO.getLangue());
            livre.setIsbn(livreRequestDTO.getIsbn());
            livre.setNbrePage(livreRequestDTO.getNbrePage());

            if (livreRequestDTO.getAuteurId() != null) {
                Auteur auteur = auteurRepository.findById(livreRequestDTO.getAuteurId())
                        .orElseThrow(() -> new IllegalArgumentException("Auteur non trouvé"));
                livre.setAuteur(auteur);
            }

            return livreRepository.save(livre);
        });
    }



    // Supprimer un livre
    public void deleteLivre(Long id) {
        livreRepository.deleteById(id);
    }
}
