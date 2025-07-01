package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    public void creerCompte();
    public void supprimerCompte();
    public void modifierProfil();
    public Utilisateur consulterSonCompte(int idUtilisateur);
    public Utilisateur voirProfilUtilisateur(int idUtilisateur);
    public void desactiverCompte(int idUtilisateur);
    public void supprimerCompte(int idUtilisateur);


}
