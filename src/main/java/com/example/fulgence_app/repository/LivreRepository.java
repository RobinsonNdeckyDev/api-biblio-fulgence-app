package com.example.fulgence_app.repository;

import com.example.fulgence_app.models.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivreRepository extends JpaRepository<Livre, Long> {
    @Override
    Optional<Livre> findById(Long id);

    boolean existsByNom(String nom);
}
