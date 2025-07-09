package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    void creerCompte(Utilisateur utilisateur);

    void supprimerCompte(long idUtilisateur);

    void modifierProfil(Utilisateur utilisateur);

    Utilisateur consulterUtilisateur(long idUtilisateur);

    Utilisateur consulterUtilisateurParPseudo(String pseudo);

    List<Utilisateur> consulterListeUtilisateurs();

    void desactiverCompte(long idUtilisateur);

    void activerCompte(long idUtilisateur);

    default Utilisateur consulterUtilisateurParPseudo(String pseudo) {
        return null;
    }
}
