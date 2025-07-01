-- Table: Utilisateurs
CREATE TABLE Utilisateurs (
    idUtilisateur INT PRIMARY KEY IDENTITY,
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
    administrateur BIT
);

-- Table: Categories
CREATE TABLE Categories (
    idCategorie INT PRIMARY KEY IDENTITY,
    libelle VARCHAR(100)
);

-- Table: Articles
CREATE TABLE Articles (
    idArticle INT PRIMARY KEY IDENTITY(1,1),
    nomArticle VARCHAR(100),
    descriptionArticle VARCHAR(MAX),
    dateDebutEncheres DATE,
    dateFinEncheres DATE,
    miseAPrix INT,
    prixVente INT,
    etatVente VARCHAR(50),
    noUtilisateur INT,
    noCategorie INT,
    FOREIGN KEY (noUtilisateur) REFERENCES Utilisateurs(idUtilisateur),
    FOREIGN KEY (noCategorie) REFERENCES Categories(idCategorie)
);

-- Table: Retraits
CREATE TABLE Retraits (
    noArticle INT PRIMARY KEY,
    rue VARCHAR(100),
    code_postal VARCHAR(10),
    ville VARCHAR(50),
    FOREIGN KEY (noArticle) REFERENCES Articles(idArticle)
);

-- Table: Encheres
CREATE TABLE Encheres (
    idUtilisateur INT,
    idArticle INT,
    dateEnchere DATE,
    montant_enchere INT,
    PRIMARY KEY (idUtilisateur, idArticle, dateEnchere),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateurs(idUtilisateur),
    FOREIGN KEY (idArticle) REFERENCES Articles(idArticle)
);
