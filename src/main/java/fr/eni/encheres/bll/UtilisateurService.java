package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    public void creerCompte(Utilisateur utilisateur);

    public void supprimerCompte(long idUtilisateur);

    public void modifierProfil(Utilisateur utilisateur);

    public Utilisateur consulterUtilisateur(long idUtilisateur);

    public List<Utilisateur> consulterListeUtilisateurs();

    public void desactiverCompte(long idUtilisateur);

    public void activerCompte(long idUtilisateur);

}
