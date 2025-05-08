// AuteurService.java
package com.example.fulgence_app.services;

import com.example.fulgence_app.dtos.AuteurRequestDTO;
import com.example.fulgence_app.dtos.UserRequestDTO;
import com.example.fulgence_app.models.Auteur;
import com.example.fulgence_app.models.User;
import com.example.fulgence_app.models.Role;
import com.example.fulgence_app.repository.AuteurRepository;
import com.example.fulgence_app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuteurService {
    private final AuteurRepository auteurRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuteurService(AuteurRepository auteurRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.auteurRepository = auteurRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Récupérer tous les auteurs
    public List<Auteur> findAll() {
        return auteurRepository.findAll();
    }

    // Récupérer un auteur par son id
    public Optional<Auteur> getAuteurById(Long id) {
        return auteurRepository.findById(id);
    }

    // Créer un nouvel auteur
    public Auteur createAuteur(AuteurRequestDTO auteurRequestDTO) {
        if (auteurRepository.existsByEmail(auteurRequestDTO.getEmail())) {
            throw new IllegalArgumentException("L'auteur avec cet email existe déjà.");
        }

        Auteur auteur = new Auteur();
        auteur.setNom(auteurRequestDTO.getNom());
        auteur.setPrenom(auteurRequestDTO.getPrenom());
        auteur.setEmail(auteurRequestDTO.getEmail());
        auteur.setPassword(passwordEncoder.encode(auteurRequestDTO.getPassword()));
        auteur.setRole(Role.AUTEUR);
        auteur.setTelephone(auteurRequestDTO.getTelephone());
        auteur.setAdresse(auteurRequestDTO.getAdresse());
        auteur.setDescription(auteurRequestDTO.getDescription());
        auteur.setPhotoProfil(auteurRequestDTO.getPhotoProfil());

        return auteurRepository.save(auteur);
    }

    // Mettre à jour un auteur
    public Optional<Auteur> updateAuteur(Long id, AuteurRequestDTO auteurRequestDTO) {
        return auteurRepository.findById(id).map(user -> {
            if (!user.getEmail().equals(auteurRequestDTO.getEmail())
                    && auteurRepository.existsByEmail(auteurRequestDTO.getEmail())) {
                throw new IllegalArgumentException("Cet email est déjà utilisé par un autre auteur.");
            }

            user.setNom(auteurRequestDTO.getNom());
            user.setPrenom(auteurRequestDTO.getPrenom());
            user.setEmail(auteurRequestDTO.getEmail());
            if (auteurRequestDTO.getPassword() != null && !auteurRequestDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(auteurRequestDTO.getPassword()));
            }
            user.setTelephone(auteurRequestDTO.getTelephone());
            user.setAdresse(auteurRequestDTO.getAdresse());
            user.setDescription(auteurRequestDTO.getDescription());
            user.setPhotoProfil(auteurRequestDTO.getPhotoProfil());

            return auteurRepository.save(user);
        });
    }

    // Supprimer un auteur
    public void deleteAuteur(Long id) {
        if (!auteurRepository.existsById(id)) {
            throw new EntityNotFoundException("Auteur non trouvé avec l'id " + id);
        }
        auteurRepository.deleteById(id);
    }
}