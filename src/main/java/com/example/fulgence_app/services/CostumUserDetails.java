package com.example.fulgence_app.services;

import com.example.fulgence_app.models.Auteur;
import com.example.fulgence_app.models.User;
import com.example.fulgence_app.repository.AuteurRepository;
import com.example.fulgence_app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CostumUserDetails implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuteurRepository auteurRepository;

    public CostumUserDetails(UserRepository userRepository, AuteurRepository auteurRepository) {
        this.userRepository = userRepository;
        this.auteurRepository = auteurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Rechercher dans la table User
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return user; // User implémente UserDetails
        }

        // Rechercher dans la table Auteur
        Auteur auteur = auteurRepository.findByEmail(email).orElse(null);
        if (auteur != null) {
            return (UserDetails) auteur; // Auteur implémente UserDetails
        }

        throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email);
    }
}
