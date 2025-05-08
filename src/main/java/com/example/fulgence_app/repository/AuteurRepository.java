package com.example.fulgence_app.repository;

import com.example.fulgence_app.models.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuteurRepository extends JpaRepository<Auteur, Long> {

    boolean existsByEmail(String email);
    Optional<Auteur>findByEmail(String email);
}
