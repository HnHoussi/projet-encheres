-- Script SQL Server pour le système de vente aux enchères
-- Création de la base de données
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'BDD_ENCHERES')
BEGIN
    CREATE DATABASE systeme_encheres;
END
GO

USE BDD_ENCHERES;
GO

-- Suppression des tables si elles existent (dans l'ordre inverse des dépendances)
IF OBJECT_ID('Enchere', 'U') IS NOT NULL DROP TABLE Encheres;
IF OBJECT_ID('ArticleVendu', 'U') IS NOT NULL DROP TABLE Articles;
IF OBJECT_ID('Retrait', 'U') IS NOT NULL DROP TABLE Retraits;
IF OBJECT_ID('Categorie', 'U') IS NOT NULL DROP TABLE Categories;
IF OBJECT_ID('Utilisateur', 'U') IS NOT NULL DROP TABLE Utilisateurs;
GO

-- Table Utilisateur
CREATE TABLE Utilisateurs (
    idUtilisateur INT IDENTITY(1,1) PRIMARY KEY,
    pseudo NVARCHAR(50) NOT NULL UNIQUE,
    nom NVARCHAR(100) NOT NULL,
    prenom NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    telephone NVARCHAR(15),
    rue NVARCHAR(255),
    codePostal NVARCHAR(10),
    ville NVARCHAR(100),
    motDePasse NVARCHAR(255) NOT NULL,
    credit DECIMAL(10,2) DEFAULT 0.00,
    administrateur BIT DEFAULT 0
);
GO

-- Table Categorie
CREATE TABLE Categories (
    idCategorie INT IDENTITY(1,1) PRIMARY KEY,
    libelle NVARCHAR(100) NOT NULL UNIQUE
);
GO

-- Table Retrait
CREATE TABLE Retraits (
    idRetrait INT IDENTITY(1,1) PRIMARY KEY,
    rue NVARCHAR(255) NOT NULL,
    codePostal NVARCHAR(10) NOT NULL,
    ville NVARCHAR(100) NOT NULL
);
GO

-- Table ArticleVendu
CREATE TABLE Articles (
    idArticle INT IDENTITY(1,1) PRIMARY KEY,
    nomArticle NVARCHAR(200) NOT NULL,
    description NVARCHAR(MAX),
    dateDebutEncheres DATETIME2 NOT NULL,
    dateFinEncheres DATETIME2 NOT NULL,
    miseAPrix DECIMAL(10,2) NOT NULL,
    prixVente DECIMAL(10,2),
    etatVente NVARCHAR(20) DEFAULT 'EN_COURS' CHECK (etatVente IN ('EN_COURS', 'TERMINE', 'ANNULE')),
    idUtilisateur INT NOT NULL,
    categorieArticle INT NOT NULL,
    lieuRetrait INT,
    
    CONSTRAINT FK_ArticleVendu_Utilisateur FOREIGN KEY (idUtilisateur) REFERENCES Utilisateurs(idUtilisateur) ON DELETE CASCADE,
    CONSTRAINT FK_ArticleVendu_Categorie FOREIGN KEY (categorieArticle) REFERENCES Categories(idCategorie),
    CONSTRAINT FK_ArticleVendu_Retrait FOREIGN KEY (lieuRetrait) REFERENCES Retraits(idRetrait),
    
    CONSTRAINT CHK_ArticleVendu_Dates CHECK (dateFinEncheres > dateDebutEncheres),
    CONSTRAINT CHK_ArticleVendu_Prix CHECK (miseAPrix > 0),
    CONSTRAINT CHK_ArticleVendu_PrixVente CHECK (prixVente IS NULL OR prixVente >= miseAPrix)
);
GO

-- Table Enchere
CREATE TABLE Enchere (
    idUtilisateur INT,
    idArticle INT,
    dateEnchere DATETIME2 NOT NULL,
    montant_enchere DECIMAL(10,2) NOT NULL,
    
    CONSTRAINT PK_Enchere PRIMARY KEY (idUtilisateur, idArticle, dateEnchere),
    CONSTRAINT FK_Enchere_Utilisateur FOREIGN KEY (idUtilisateur) REFERENCES Utilisateurs(idUtilisateur) ON DELETE CASCADE,
    CONSTRAINT FK_Enchere_Article FOREIGN KEY (idArticle) REFERENCES Articles(idArticle) ON DELETE CASCADE,
    
    CONSTRAINT CHK_Enchere_Montant CHECK (montant_enchere > 0)
);
GO

-- Index pour améliorer les performances
CREATE INDEX IX_ArticleVendu_Dates ON Articles(dateDebutEncheres, dateFinEncheres);
CREATE INDEX IX_ArticleVendu_Etat ON Articles(etatVente);
CREATE INDEX IX_Enchere_Date ON Enchere(dateEnchere);
CREATE INDEX IX_Enchere_Montant ON Enchere(montant_enchere);
CREATE INDEX IX_Utilisateur_Email ON Utilisateurs(email);
CREATE INDEX IX_Utilisateur_Pseudo ON Utilisateurs(pseudo);
GO

-- Insertion de données d'exemple

-- Insertion des catégories
INSERT INTO Categories (libelle) VALUES 
    (N'Informatique'),
    (N'Ameublement'),
    (N'Vêtement'),
    (N'Sport & Loisirs'),
    (N'Bricolage'),
    (N'Décoration'),
    (N'Electroménager'),
    (N'Livres'),
    (N'Art & Antiquités'),
    (N'Automobile');
GO

-- Insertion des lieux de retrait
INSERT INTO Retraits (rue, codePostal, ville) VALUES 
    (N'12 rue de la Paix', N'35000', N'Rennes'),
    (N'45 avenue des Champs', N'44000', N'Nantes'),
    (N'78 boulevard Saint-Michel', N'29000', N'Brest'),
    (N'23 place du Marché', N'56000', N'Vannes'),
    (N'67 rue Victor Hugo', N'22000', N'Saint-Brieuc');
GO

-- Insertion des utilisateurs
SET IDENTITY_INSERT Utilisateurs ON;
INSERT INTO Utilisateurs (idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur) VALUES 
    (1, N'admin', N'Martin', N'Jean', N'admin@encheres.fr', N'0299123456', N'1 rue Admin', N'35000', N'Rennes', N'password123', 1000.00, 1),
    (2, N'alice_d', N'Dupont', N'Alice', N'alice.dupont@email.fr', N'0299234567', N'15 rue des Roses', N'35000', N'Rennes', N'alice123', 500.00, 0),
    (3, N'bob_m', N'Martin', N'Bob', N'bob.martin@email.fr', N'0240345678', N'22 avenue de la Liberté', N'44000', N'Nantes', N'bob456', 750.00, 0),
    (4, N'claire_l', N'Lemoine', N'Claire', N'claire.lemoine@email.fr', N'0298456789', N'8 place de la République', N'29000', N'Brest', N'claire789', 300.00, 0),
    (5, N'david_r', N'Rousseau', N'David', N'david.rousseau@email.fr', N'0297567890', N'33 rue de la Gare', N'56000', N'Vannes', N'david012', 200.00, 0);
SET IDENTITY_INSERT Utilisateurs OFF;
GO

-- Insertion des articles en vente
SET IDENTITY_INSERT Articles ON;
INSERT INTO Articles (idArticle, nomArticle, description, dateDebutEncheres, dateFinEncheres, miseAPrix, idUtilisateur, categorieArticle, lieuRetrait) VALUES 
    (1, N'Ordinateur portable Dell', N'Dell Inspiron 15, 8GB RAM, 256GB SSD, état excellent', '2025-07-01 09:00:00', '2025-07-08 18:00:00', 250.00, 2, 1, 1),
    (2, N'Canapé 3 places', N'Canapé en cuir noir, très bon état, peu servi', '2025-07-02 10:00:00', '2025-07-09 20:00:00', 150.00, 3, 2, 2),
    (3, N'Vélo de course', N'Vélo Peugeot vintage, restauré, parfait état', '2025-07-01 14:00:00', '2025-07-07 16:00:00', 80.00, 4, 4, 3),
    (4, N'Collection de livres', N'Romans classiques français, éditions reliées', '2025-07-03 08:00:00', '2025-07-10 19:00:00', 30.00, 5, 8, 4),
    (5, N'Perceuse sans fil', N'Bosch Professional, avec 2 batteries et accessoires', '2025-07-02 11:00:00', '2025-07-08 17:00:00', 60.00, 2, 5, 1);
SET IDENTITY_INSERT ArticleVendu OFF;
GO

-- Insertion des enchères
INSERT INTO Encheres (idUtilisateur, idArticle, dateEnchere, montant_enchere) VALUES 
    (3, 1, '2025-07-01 10:30:00', 260.00),
    (4, 1, '2025-07-01 15:20:00', 280.00),
    (5, 1, '2025-07-02 09:15:00', 300.00),
    
    (2, 2, '2025-07-02 11:45:00', 160.00),
    (4, 2, '2025-07-02 16:30:00', 180.00),
    
    (2, 3, '2025-07-01 16:00:00', 85.00),
    (3, 3, '2025-07-02 08:30:00', 95.00),
    (5, 3, '2025-07-02 14:45:00', 110.00),
    
    (2, 4, '2025-07-03 09:20:00', 35.00),
    (3, 4, '2025-07-03 18:00:00', 40.00),
    
    (3, 5, '2025-07-02 12:30:00', 65.00),
    (4, 5, '2025-07-02 17:15:00', 75.00);
GO

-- Vues utiles pour le système

-- Vue des enchères en cours avec les détails
CREATE VIEW EncheresEnCours AS
SELECT 
    a.idArticle,
    a.nomArticle,
    a.description,
    a.dateFinEncheres,
    a.miseAPrix,
    u.pseudo as vendeur,
    c.libelle as categorie,
    ISNULL(MAX(e.montant_enchere), a.miseAPrix) as prixActuel,
    COUNT(e.montant_enchere) as nombreEncheres
FROM Articles a
LEFT JOIN Encheres e ON a.idArticle = e.idArticle
INNER JOIN Utilisateurs u ON a.idUtilisateur = u.IdUtilisateur
INNER JOIN Categories c ON a.categorieArticle = c.Categories
WHERE a.etatVente = 'EN_COURS' 
    AND a.dateFinEncheres > GETDATE()
GROUP BY a.idArticle, a.nomArticle, a.description, a.dateFinEncheres, a.miseAPrix, u.pseudo, c.libelle;
GO

-- Vue des meilleures enchères par article
CREATE VIEW MeilleuresEncheres AS
SELECT 
    e.idArticle,
    a.nomArticle,
    e.idUtilisateur,
    u.pseudo,
    e.montant_enchere,
    e.dateEnchere
FROM Encheres e
INNER JOIN Articles a ON e.idArticle = a.idArticle
INNER JOIN Utilisateurs u ON e.idUtilisateur = u.idUtilisateur
WHERE e.montant_enchere = (
    SELECT MAX(e2.montant_enchere)
    FROM Encheres e2
    WHERE e2.idArticle = e.idArticle
);
GO

-- Procédures stockées utiles

-- Procédure pour placer une enchère
CREATE PROCEDURE PlacerEnchere
    @p_idUtilisateur INT,
    @p_idArticle INT,
    @p_montant DECIMAL(10,2)
AS
BEGIN
    DECLARE @v_miseAPrix DECIMAL(10,2);
    DECLARE @v_maxEnchere DECIMAL(10,2);
    DECLARE @v_dateFinEncheres DATETIME2;
    DECLARE @v_etatVente NVARCHAR(20);
    
    BEGIN TRY
        -- Vérifications
        SELECT @v_miseAPrix = miseAPrix, 
               @v_dateFinEncheres = dateFinEncheres, 
               @v_etatVente = etatVente
        FROM Articles
        WHERE idArticle = @p_idArticle;
        
        SELECT @v_maxEnchere = ISNULL(MAX(montant_enchere), 0)
        FROM Encheres
        WHERE idArticle = @p_idArticle;
        
        -- Contrôles
        IF @v_etatVente != 'EN_COURS'
        BEGIN
            RAISERROR('L''article n''est plus en vente', 16, 1);
            RETURN;
        END
        
        IF @v_dateFinEncheres <= GETDATE()
        BEGIN
            RAISERROR('Les enchères sont terminées', 16, 1);
            RETURN;
        END
        
        IF @p_montant <= (CASE WHEN @v_miseAPrix > @v_maxEnchere THEN @v_miseAPrix ELSE @v_maxEnchere END)
        BEGIN
            RAISERROR('L''enchère doit être supérieure au prix actuel', 16, 1);
            RETURN;
        END
        
        -- Insertion de l'enchère
        INSERT INTO Encheres (idUtilisateur, idArticle, dateEnchere, montant_enchere)
        VALUES (@p_idUtilisateur, @p_idArticle, GETDATE(), @p_montant);
        
        PRINT 'Enchère placée avec succès';
        
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END
GO

-- Fonction pour obtenir le prix actuel d'un article
CREATE FUNCTION GetPrixActuel(@idArticle INT)
RETURNS DECIMAL(10,2)
AS
BEGIN
    DECLARE @prixActuel DECIMAL(10,2);
    DECLARE @miseAPrix DECIMAL(10,2);
    DECLARE @maxEnchere DECIMAL(10,2);
    
    SELECT @miseAPrix = miseAPrix FROM Article WHERE idArticle = @idArticle;
    SELECT @maxEnchere = MAX(montant_enchere) FROM Enchere WHERE idArticle = @idArticle;
    
    SET @prixActuel = ISNULL(@maxEnchere, @miseAPrix);
    
    RETURN @prixActuel;
END
GO

-- Affichage des données pour vérification
PRINT 'Utilisateurs créés:';
SELECT idUtilisateur, pseudo, nom, prenom, email FROM Utilisateur;

PRINT 'Catégories créées:';
SELECT * FROM Categories;

PRINT 'Articles en vente:';
SELECT a.idArticle, a.nomArticle, u.pseudo as vendeur, c.libelle as categorie, a.miseAPrix 
FROM Article a 
INNER JOIN Utilisateurs u ON a.idUtilisateur = u.idUtilisateur
INNER JOIN Categories c ON a.categorieArticle = c.idCategorie;

PRINT 'Enchères actuelles:';
SELECT * FROM EncheresEnCours;

