---------------------------------------------------
-- Insertion des catégories
INSERT INTO Categories (libelle) VALUES 
('Informatique'),
('Livres'),
('Maison'),
('Jeux vidéo'),
('Sport'),
('Musique'),
('Cuisine');

---------------------------------------------------
-- Insertion des utilisateurs
INSERT INTO Utilisateurs
(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur, compteActif)
VALUES
('john_doe', 'Doe', 'John', 'john@example.com', '0601020304', '1 rue de Paris', '44000', 'Nantes', 'hashed_password_1', 1000, 0, 1),
('jane_smith', 'Smith', 'Jane', 'jane@example.com', '0605060708', '2 rue de Lyon', '69000', 'Lyon', 'hashed_password_2', 500, 0, 1),
('admin_user', 'Admin', 'User', 'admin@example.com', '0611121314', '10 rue d''Admin', '75000', 'Paris', 'hashed_password_3', 2000, 1, 1),
('alice33', 'Durand', 'Alice', 'alice@example.com', '0622334455', '3 rue de Bordeaux', '33000', 'Bordeaux', 'hashed_password_4', 800, 0, 1),
('ben75', 'Martin', 'Benjamin', 'ben@example.com', '0644556677', '4 avenue de Paris', '75015', 'Paris', 'hashed_password_5', 1200, 0, 1),
('charlie44', 'Petit', 'Charlie', 'charlie@example.com', '0666778899', '5 rue de Nantes', '44000', 'Nantes', 'hashed_password_6', 600, 0, 1);

---------------------------------------------------
-- Insertion des articles
INSERT INTO Articles
(nomArticle, description, dateDebutEnchere, dateFinEnchere, miseAPrix, prixVente, etatVente, idUtilisateur, idCategorie)
VALUES
-- Anciens articles déjà terminés ou en cours
('Ordinateur portable', 'PC gamer avec RTX 3070', '2025-07-01 10:00:00', '2025-07-10 10:00:00', 500, 750, 'EN_COURS', 1, 1),
('Roman fantasy', 'Roman d''heroic fantasy en excellent état', '2025-07-02 12:00:00', '2025-07-09 12:00:00', 10, 25, 'EN_COURS', 2, 2),
('Canapé 3 places', 'Canapé en tissu gris, très confortable', '2025-07-03 09:00:00', '2025-07-12 09:00:00', 100, 150, 'EN_COURS', 1, 3),
('Jeu vidéo PS5', 'Jeu neuf sous blister', '2025-07-04 14:00:00', '2025-07-10 14:00:00', 30, 45, 'EN_COURS', 3, 4),
('Fauteuil vintage', 'A vendre plus tard', '2025-07-20 10:00:00', '2025-07-25 10:00:00', 80, NULL, 'NON_DEBUTE', 1, 3),
('Clavier mécanique', 'En parfait état', '2025-06-20 10:00:00', '2025-06-25 10:00:00', 40, 55, 'TERMINE', 1, 1),
('BD collection', 'Rare et recherchée', '2025-06-18 10:00:00', '2025-06-22 10:00:00', 20, 35, 'TERMINE', 2, 2),
('Guitare acoustique', 'Yamaha, très bon état', '2025-07-05 10:00:00', '2025-07-15 10:00:00', 150, NULL, 'EN_COURS', 4, 6),
('Ballon de foot', 'Ballon officiel Ligue 1', '2025-07-06 12:00:00', '2025-07-16 12:00:00', 30, NULL, 'EN_COURS', 5, 5),
('Robot de cuisine', 'Robot multifonction dernier modèle', '2025-07-20 09:00:00', '2025-07-27 09:00:00', 200, NULL, 'NON_DEBUTE', 1, 7),
('Casque audio', 'Casque Bose avec réduction de bruit', '2025-06-15 09:00:00', '2025-06-20 09:00:00', 100, 140, 'TERMINE', 4, 6),
('Raquette de tennis', 'Wilson, peu servie', '2025-06-10 14:00:00', '2025-06-18 14:00:00', 50, 80, 'TERMINE', 5, 5);

---------------------------------------------------
-- Insertion des retraits
INSERT INTO Retraits (idArticle, rue, codePostal, ville)
VALUES
(1, '1 rue de Paris', '44000', 'Nantes'),
(2, '2 rue de Lyon', '69000', 'Lyon'),
(3, '1 rue de Paris', '44000', 'Nantes'),
(4, '10 rue d''Admin', '75000', 'Paris'),
(8, '3 rue de Bordeaux', '33000', 'Bordeaux'),
(9, '4 avenue de Paris', '75015', 'Paris'),
(10, '5 rue de Nantes', '44000', 'Nantes'),
(11, '3 rue de Bordeaux', '33000', 'Bordeaux'),
(12, '4 avenue de Paris', '75015', 'Paris');

---------------------------------------------------
-- Insertion des enchères
INSERT INTO Encheres (idUtilisateur, idArticle, dateEnchere, montantEnchere)
VALUES
-- Anciens articles
(2, 1, '2025-07-01 11:00:00', 550),
(3, 1, '2025-07-01 12:00:00', 600),
(2, 3, '2025-07-01 13:00:00', 650),
(1, 2, '2025-07-02 13:00:00', 15),
(3, 2, '2025-07-02 14:00:00', 20),
(1, 4, '2025-07-02 15:00:00', 25),
(1, 6, '2025-06-25 09:00:00', 55),
(3, 6, '2025-06-24 15:00:00', 50),
(2, 7, '2025-06-21 15:00:00', 30),
(1, 8, '2025-07-05 11:00:00', 160),
(5, 8, '2025-07-05 12:00:00', 170),
(3, 8, '2025-07-05 13:00:00', 180),
(4, 9, '2025-07-06 13:00:00', 35),
(6, 9, '2025-07-06 14:00:00', 40),
(3, 9, '2025-07-06 15:00:00', 45),
(1, 11, '2025-06-16 10:00:00', 120),
(3, 11, '2025-06-17 11:00:00', 140),
(2, 12, '2025-06-11 12:00:00', 70),
(3, 12, '2025-06-12 13:00:00', 80);
