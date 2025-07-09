-- Insert into Categories
INSERT INTO Categories (libelle) VALUES ('Informatique');
INSERT INTO Categories (libelle) VALUES ('Livres');
INSERT INTO Categories (libelle) VALUES ('Maison');
INSERT INTO Categories (libelle) VALUES ('Jeux vidéo');

---------------------------------------------------
-- Insert into Utilisateurs
INSERT INTO Utilisateurs 
(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur, compteActif)
VALUES 
('john_doe', 'Doe', 'John', 'john@example.com', '0601020304', '1 rue de Paris', '44000', 'Nantes', 'hashed_password_1', 1000, 0, 1),
('jane_smith', 'Smith', 'Jane', 'jane@example.com', '0605060708', '2 rue de Lyon', '69000', 'Lyon', 'hashed_password_2', 500, 0, 1),
('admin_user', 'Admin', 'User', 'admin@example.com', '0611121314', '10 rue d''Admin', '75000', 'Paris', 'hashed_password_3', 2000, 1, 1);

---------------------------------------------------
-- Insert into Articles
INSERT INTO Articles 
(nomArticle, description, dateDebutEnchere, dateFinEnchere, miseAPrix, prixVente, etatVente, idUtilisateur, idCategorie)
VALUES
('Ordinateur portable', 'PC gamer avec RTX 3070', '2025-07-01 10:00:00', '2025-07-10 10:00:00', 500, 750, 'En cours', 1, 1),
('Roman fantasy', 'Roman d''heroic fantasy en excellent état', '2025-07-02 12:00:00', '2025-07-09 12:00:00', 10, 25, 'En cours', 2, 2),
('Canapé 3 places', 'Canapé en tissu gris, très confortable', '2025-07-03 09:00:00', '2025-07-12 09:00:00', 100, 150, 'En cours', 1, 3),
('Jeu vidéo PS5', 'Jeu neuf sous blister', '2025-07-04 14:00:00', '2025-07-08 14:00:00', 30, 45, 'En cours', 3, 4);

---------------------------------------------------
-- Insert into Retraits (use article IDs starting from 1)
INSERT INTO Retraits (idArticle, rue, codePostal, ville)
VALUES
(1, '1 rue de Paris', '44000', 'Nantes'),
(2, '2 rue de Lyon', '69000', 'Lyon'),
(3, '1 rue de Paris', '44000', 'Nantes'),
(4, '10 rue d''Admin', '75000', 'Paris');

---------------------------------------------------
-- Insert into Encheres
INSERT INTO Encheres (idUtilisateur, idArticle, dateEnchere, montantEnchere)
VALUES
(2, 1, '2025-07-01 11:00:00', 550),
(3, 1, '2025-07-01 12:00:00', 600),
(2, 3, '2025-07-01 13:00:00', 650),
(1, 2, '2025-07-02 13:00:00', 15),
(3, 2, '2025-07-02 14:00:00', 20),
(1, 4, '2025-07-02 15:00:00', 25);

--Insère les utilisateurs/
INSERT INTO [UTILISATEURS] (pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur, compteActif) VALUES ('abaille@campus-eni.fr','baille','alice','abaille@campus-eni.fr',0666666666,'rue de la rue',44000,'Nantes','{bcrypt}$2a$10$KaSHgvH9p/cUnsOVPzYvzunWDAIv68whrOxmui1S.0AjzbP5RX7yO',200,0,1);/*Mot de Passe = annelise*/
INSERT INTO [UTILISATEURS] (pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur, compteActif) VALUES ('sgobin@campus-eni.fr','gobin','stephane','sgobin@campus-eni.fr',0777777777,'rue de la rue',44000,'Nantes','{bcrypt}$2a$10$B5U29ajHsIKd8aY3c/JNn.xTJpOCAeoXvT9XvfzbbHGP4iIFV9Lkm',200,1,1);/*Mot de Passe = stephane*/

--Insère des enregistrements dans la table ROLES
INSERT INTO [ROLES] (idUtilisateur, role) VALUES (6,'ROLE_UTILISATEUR');
INSERT INTO [ROLES] (idUtilisateur, role) VALUES (7,'ROLE_UTILISATEUR');
INSERT INTO [ROLES] (idUtilisateur, role) VALUES (6,'ROLE_ADMIN');
INSERT INTO [ROLES] (idUtilisateur, role) VALUES (7,'ROLE_ADMIN');

INSERT INTO [ROLES] (idUtilisateur, role) VALUES (1,'ROLE_UTILISATEUR');
INSERT INTO [ROLES] (idUtilisateur, role) VALUES (2,'ROLE_UTILISATEUR');

UPDATE UTILISATEURS
SET MOTDEPASSE = '$2a$10$Z2XhsWXDH5Vr9RVJqDUFgOdpB3d1VUnsmfGZH2EO9VSOu3mDz3cQC'
WHERE PSEUDO = 'sgobin@campus-eni.fr';