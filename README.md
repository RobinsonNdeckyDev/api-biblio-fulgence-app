# API de Gestion de Bibliothèque

Une API REST développée avec Spring Boot permettant la gestion des livres et des auteurs.

## Technologies Utilisées

- Java 17
- Spring Boot
- Maven
- Spring Data JPA
- Base de données (à préciser)

## Fonctionnalités

### Gestion des Livres

- Récupération de tous les livres
- Récupération d'un livre par son ID
- Création d'un nouveau livre
- Modification d'un livre existant
- Suppression d'un livre

## Points d'accès (Endpoints)

### Livres

```http
GET /api/livres                  # Récupérer tous les livres
GET /api/livres/{id}            # Récupérer un livre par ID
POST /api/livres/create-livre    # Créer un nouveau livre
PUT /api/livres/{id}            # Modifier un livre
DELETE /api/livres/{id}         # Supprimer un livre

{
  "id": "Long",
  "nom": "String",
  "titre": "String",
  "isbn": "Long",
  "langue": "String",
  "nbrePage": "Long",
  "auteurId": "Long"
}

## Installation et Démarrage
1- Cloner le repository
git clone [URL_DU_REPO]

2- Compiler le projet
mvn clean install

3- Lancer l'application
mvn spring-boot:run

## Documentation API (Swagger)

La documentation Swagger de l'API est disponible à l'adresse suivante après le démarrage de l'application :

```http
http://localhost:8080/swagger-ui/index.html


### Exemple de Documentation Swagger
## Endpoints Livres

Méthode
URL
Description
GET
/api/livres
Liste tous les livres
GET
/api/livres/{id}
Obtient un livre par son ID
POST
/api/livres/create-livre
Crée un nouveau livre
PUT
/api/livres/{id}
Met à jour un livre existant
DELETE
/api/livres/{id}
Supprime un livre
Modèles de Requêtes
Création d'un Livre (POST)

### Codes de Retour
Code
Description
200
Succès
201
Création réussie
400
Requête invalide
404
Ressource non trouvée
500
Erreur serveur

