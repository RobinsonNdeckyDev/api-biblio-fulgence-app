package com.example.fulgence_app.config;

import com.example.fulgence_app.models.User;
import com.example.fulgence_app.models.Role;
import com.example.fulgence_app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Vérifier si le super admin existe déjà
            if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
                // Créer l'utilisateur super admin
                User superAdmin = new User();
                superAdmin.setPrenom("Super");
                superAdmin.setNom("Admin");
                superAdmin.setEmail("admin@admin.com");
                superAdmin.setPassword(passwordEncoder.encode("Admin123@")); // Mot de passe sécurisé
                superAdmin.setTelephone("+221 77 000 00 00");
                superAdmin.setAdresse("Dakar, Sénégal");
                superAdmin.setPhotoProfil("default-admin.jpg");
                superAdmin.setDescription("Compte Super Administrateur");
                superAdmin.setRole(Role.SUPER_ADMIN);

                userRepository.save(superAdmin);

                System.out.println("Super Admin créé avec succès!");
            }
        };
    }
}