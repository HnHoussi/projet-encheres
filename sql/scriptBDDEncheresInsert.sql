use [BDD-ENCHERES]

INSERT INTO Utilisateurs (pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur)
VALUES
('user1', 'Doe', 'John', 'john@example.com', '0600000000', '123 Rue A', '75000', 'Paris', 'password123', 100, 0),
('admin1', 'Smith', 'Alice', 'alice@example.com', '0611111111', '456 Rue B', '69000', 'Lyon', 'adminpass456', 500, 1);

INSERT INTO Categories (libelle)
VALUES
    ('Informatique'),
    ('Livres'),
    ('Mode');

INSERT INTO Articles (nomArticle, descriptionArticle, dateDebutEncheres, dateFinEncheres, miseAPrix, prixVente, etatVente, noUtilisateur, noCategorie)
VALUES
    ('Ordinateur Portable', 'PC Gamer très performant', '2025-07-01', '2025-07-10', 500, 650, 'En cours', 1, 1),
    ('Roman Fantastique', 'Une aventure épique', '2025-07-02', '2025-07-09', 10, 20, 'En cours', 2, 2);

INSERT INTO Retraits (noArticle, rue, code_postal, ville)
VALUES
    (1, '123 Rue A', '75000', 'Paris'),
    (2, '456 Rue B', '69000', 'Lyon');

INSERT INTO Encheres (idUtilisateur, idArticle, dateEnchere, montant_enchere)
VALUES
    (2, 1, '2025-07-03', 600),
    (1, 2, '2025-07-03', 15);
