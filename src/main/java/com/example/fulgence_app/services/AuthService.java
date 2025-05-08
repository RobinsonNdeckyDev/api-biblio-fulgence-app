package com.example.fulgence_app.services;

                       import com.example.fulgence_app.dtos.LoginRequest;
                       import com.example.fulgence_app.dtos.LoginResponse;
                       import com.example.fulgence_app.dtos.UserRequestDTO;
                       import com.example.fulgence_app.models.Auteur;
                       import com.example.fulgence_app.models.User;
                       import com.example.fulgence_app.models.Role;
                       import com.example.fulgence_app.repository.AuteurRepository;
                       import com.example.fulgence_app.repository.UserRepository;
                       import com.example.fulgence_app.utils.Util;
                       import lombok.extern.slf4j.Slf4j;
                       import org.springframework.security.authentication.AuthenticationManager;
                       import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
                       import org.springframework.security.core.Authentication;
                       import org.springframework.security.core.AuthenticationException;
                       import org.springframework.security.core.userdetails.UserDetails;
                       import org.springframework.security.crypto.password.PasswordEncoder;
                       import org.springframework.stereotype.Service;

                       @Service
                       @Slf4j
                       public class AuthService {
                           private final UserRepository userRepository;
                           private final AuteurRepository auteurRepository;
                           private final Util jwtUtil;
                           private final AuthenticationManager authenticationManager;
                           private final PasswordEncoder passwordEncoder;

                           public AuthService(UserRepository userRepository,
                                             Util jwtUtil,
                                             AuthenticationManager authenticationManager,
                                             PasswordEncoder passwordEncoder,
                                              AuteurRepository auteurRepository) {
                               this.userRepository = userRepository;
                               this.jwtUtil = jwtUtil;
                               this.authenticationManager = authenticationManager;
                               this.passwordEncoder = passwordEncoder;
                                 this.auteurRepository = auteurRepository;
                           }

                           // AuthService.java
//                           public LoginResponse login(LoginRequest request) {
//                               try {
//                                   log.info("Tentative de connexion pour l'utilisateur: {}", request.getEmail());
//
//                                   // Authentifier l'utilisateur
//                                   Authentication authentication = authenticationManager.authenticate(
//                                           new UsernamePasswordAuthenticationToken(
//                                                   request.getEmail(),
//                                                   request.getPassword()
//                                           )
//                                   );
//
//                                   // Rechercher l'utilisateur dans la table User
//                                   User user = userRepository.findByEmail(request.getEmail()).orElse(null);
//
//                                   if (user != null) {
//                                       // Générer le token pour User
//                                       String token = jwtUtil.generateToken(user);
//
//                                       LoginResponse.UserInfo info = new LoginResponse.UserInfo(
//                                               user.getId(),
//                                               user.getId(),
//                                               user.getPrenom(),
//                                               user.getNom(),
//                                               user.getEmail(),
//                                               user.getRole().name(),
//                                               user.getTelephone(),
//                                               user.getDescription(),
//                                               user.getAdresse(),
//                                               user.getPhotoProfil()
//                                       );
//
//                                       log.info("Connexion réussie pour l'utilisateur: {}", request.getEmail());
//                                       return new LoginResponse(token, info, user.getRole().name());
//                                   }
//
//                                   // Rechercher l'utilisateur dans la table Auteur
//                                   Auteur auteur = auteurRepository.findByEmail(request.getEmail())
//                                           .orElseThrow(() -> new RuntimeException("auteur introuvable"));
//
//                                   // Générer le token pour Auteur
//                                   String token = jwtUtil.generateToken((UserDetails) auteur);
//
//                                   LoginResponse.UserInfo info = new LoginResponse.UserInfo(
//                                           auteur.getId(),
//                                           auteur.getId(),
//                                           auteur.getPrenom(),
//                                           auteur.getNom(),
//                                           auteur.getEmail(),
//                                           auteur.getRole().name(),
//                                           auteur.getTelephone(),
//                                           auteur.getDescription(),
//                                           auteur.getAdresse(),
//                                           auteur.getPhotoProfil()
//                                   );
//
//                                   log.info("Connexion réussie pour l'auteur: {}", request.getEmail());
//                                   return new LoginResponse(token, info, auteur.getRole().name());
//
//                               } catch (AuthenticationException e) {
//                                   log.error("Échec de l'authentification pour {}: {}", request.getEmail(), e.getMessage());
//                                   throw new RuntimeException("Identifiants invalides");
//                               }
//                           }

                            public LoginResponse login(LoginRequest request) {
                                 try {
                                      log.info("Tentative de connexion pour l'utilisateur: {}", request.getEmail());

                                      // Authentifier l'utilisateur
                                      Authentication authentication = authenticationManager.authenticate(
                                             new UsernamePasswordAuthenticationToken(
                                                    request.getEmail(),
                                                    request.getPassword()
                                             )
                                      );

                                      // Rechercher l'utilisateur dans la table User
                                      User user = userRepository.findByEmail(request.getEmail()).orElse(null);

                                      if (user != null) {
                                        // Générer le token pour User
                                        String token = jwtUtil.generateToken(user);

                                        LoginResponse.UserInfo info = new LoginResponse.UserInfo(
                                                  user.getId(),
                                                  user.getId(),
                                                  user.getPrenom(),
                                                  user.getNom(),
                                                  user.getEmail(),
                                                  user.getRole().name(),
                                                  user.getTelephone(),
                                                  user.getDescription(),
                                                  user.getAdresse(),
                                                  user.getPhotoProfil()
                                        );

                                        log.info("Connexion réussie pour l'utilisateur: {}", request.getEmail());
                                        return new LoginResponse(token, info, user.getRole().name());
                                      }

                                      // Rechercher l'utilisateur dans la table Auteur
                                      Auteur auteur = auteurRepository.findByEmail(request.getEmail())
                                             .orElseThrow(() -> new RuntimeException("auteur introuvable"));

                                      // Générer le token pour Auteur
                                      String token = jwtUtil.generateToken((UserDetails) auteur);

                                      LoginResponse.UserInfo info = new LoginResponse.UserInfo(
                                             auteur.getId(),
                                             auteur.getId(),
                                             auteur.getPrenom(),
                                             auteur.getNom(),
                                             auteur.getEmail(),
                                             auteur.getRole().name(),
                                             auteur.getTelephone(),
                                             auteur.getDescription(),
                                             auteur.getAdresse(),
                                             auteur.getPhotoProfil()
                                      );

                                      log.info("Connexion réussie pour l'auteur: {}", request.getEmail());
                                      return new LoginResponse(token, info, auteur.getRole().name());

                                 } catch (AuthenticationException e) {
                                      log.error("Échec de l'authentification pour {}: {}", request.getEmail(), e.getMessage());
                                      throw new RuntimeException("Identifiants invalid es");
                                    }
                            }


                           public User register(UserRequestDTO request) {
                               log.info("Tentative d'inscription pour l'utilisateur: {}", request.getEmail());

                               if (userRepository.existsByEmail(request.getEmail())) {
                                   log.warn("Tentative d'inscription avec un email déjà existant: {}", request.getEmail());
                                   throw new RuntimeException("Un utilisateur avec cet email existe déjà");
                               }

                               try {
                                   User newUser = new User();
                                   newUser.setEmail(request.getEmail());
                                   // Encoder le mot de passe avant de le sauvegarder
                                   newUser.setPassword(passwordEncoder.encode(request.getPassword()));
                                   newUser.setPrenom(request.getPrenom());
                                   newUser.setNom(request.getNom());
                                   newUser.setAdresse(request.getAdresse());
                                   newUser.setTelephone(request.getTelephone());
                                   newUser.setDescription(request.getDescription());
                                   newUser.setPhotoProfil(request.getPhotoProfil());
                                   newUser.setRole(Role.valueOf(request.getRole().toUpperCase()));

                                   User savedUser = userRepository.save(newUser);
                                   log.info("Inscription réussie pour l'utilisateur: {}", request.getEmail());
                                   return savedUser;

                               } catch (Exception e) {
                                   log.error("Erreur lors de l'inscription de l'utilisateur {}: {}", request.getEmail(), e.getMessage());
                                   throw new RuntimeException("Erreur lors de l'inscription");
                               }
                           }
                       }