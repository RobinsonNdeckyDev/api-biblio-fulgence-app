package com.example.fulgence_app.services;

import com.example.fulgence_app.dtos.UserRequestDTO;
import com.example.fulgence_app.models.User;
import com.example.fulgence_app.models.Role;
import com.example.fulgence_app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Récupérer tous les utilisateurs
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Récupérer un user par son id
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Créer un user
    public User createUser(
            String nom,
            String prenom,
            String email,
            String password,
            String role,
            String telephone,
            String adresse,
            String description,
            String photoProfil
    ) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Le user avec cet email existe déjà.");
        }

        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.valueOf(role.toUpperCase()));
        user.setTelephone(telephone);
        user.setAdresse(adresse);
        user.setDescription(description);
        user.setPhotoProfil(photoProfil);

        return userRepository.save(user);
    }

    // Modifier un user
    public Optional<User> updateUser(Long id, UserRequestDTO userRequestDTO) {
        return userRepository.findById(id).map(user -> {
            if (!user.getEmail().equals(userRequestDTO.getEmail())
                    && userRepository.existsByEmail(userRequestDTO.getEmail())) {
                throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur.");
            }

            user.setNom(userRequestDTO.getNom());
            user.setPrenom(userRequestDTO.getPrenom());
            user.setEmail(userRequestDTO.getEmail());
            if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            }
            user.setRole(Role.valueOf(userRequestDTO.getRole().toUpperCase()));
            user.setTelephone(userRequestDTO.getTelephone());
            user.setAdresse(userRequestDTO.getAdresse());
            user.setDescription(userRequestDTO.getDescription());
            user.setPhotoProfil(userRequestDTO.getPhotoProfil());

            return userRepository.save(user);
        });
    }

    // Supprimer un user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
