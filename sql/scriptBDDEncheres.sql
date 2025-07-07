Drop table if exists Retraits;
Drop table if exists Encheres;
Drop table if exists Articles;
Drop table if exists Categories;
Drop table if exists Utilisateurs;

-- Table: Utilisateurs
CREATE TABLE Utilisateurs (
    idUtilisateur BIGINT PRIMARY KEY IDENTITY,
    pseudo VARCHAR(50),
    nom VARCHAR(50),
    prenom VARCHAR(50),
    email VARCHAR(100),
    telephone VARCHAR(20),
    rue VARCHAR(100),
    codePostal VARCHAR(5),
    ville VARCHAR(50),
    motDePasse VARCHAR(100),
    credit INT,
    administrateur BIT,
    compteActif BIT
);

-- Table: Categories
CREATE TABLE Categories (
    idCategorie BIGINT PRIMARY KEY IDENTITY,
    libelle VARCHAR(100)
);

-- Table: Articles
CREATE TABLE Articles (
    idArticle BIGINT PRIMARY KEY IDENTITY(1,1),
    nomArticle VARCHAR(100),
    description VARCHAR(MAX),
    dateDebutEnchere DATETIME,
    dateFinEnchere DATETIME,
    miseAPrix INT,
    prixVente INT,
    etatVente VARCHAR(50),
    idUtilisateur BIGINT,
    idCategorie BIGINT,
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateurs(idUtilisateur),
    FOREIGN KEY (idCategorie) REFERENCES Categories(idCategorie)
);

-- Table: Retraits
CREATE TABLE Retraits (
    idArticle BIGINT PRIMARY KEY,
    rue VARCHAR(100),
    codePostal VARCHAR(10),
    ville VARCHAR(50),
    FOREIGN KEY (idArticle) REFERENCES Articles(idArticle)
);

-- Table: Encheres
CREATE TABLE Encheres (
    idUtilisateur BIGINT,
    idArticle BIGINT,
    dateEnchere DATETIME,
    montantEnchere INT,
    PRIMARY KEY (idUtilisateur, idArticle, dateEnchere),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateurs(idUtilisateur),
    FOREIGN KEY (idArticle) REFERENCES Articles(idArticle)
);
