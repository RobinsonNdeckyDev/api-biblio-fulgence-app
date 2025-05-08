package com.example.fulgence_app.controllers;

import com.example.fulgence_app.dtos.UserRequestDTO;
import com.example.fulgence_app.models.User;
import com.example.fulgence_app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Liste des users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // Détails d'un user par ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Enregistrement d'un user
    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody UserRequestDTO userRequestTDO) {
        try {


            User user = userService.createUser(
                    userRequestTDO.getNom(),
                    userRequestTDO.getPrenom(),
                    userRequestTDO.getEmail(),
                    userRequestTDO.getPassword(),
                    userRequestTDO.getAdresse(),
                    userRequestTDO.getPhotoProfil(),
                    userRequestTDO.getRole(),
                    userRequestTDO.getTelephone(),
                    userRequestTDO.getDescription()
            );
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // Mise à jour d'un user
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        try {
            Optional<User> updatedUser = userService.updateUser(id, userRequestDTO);
            return updatedUser.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Erreur interne."));
        }
    }

    // Suppression d'un user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuteur(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
