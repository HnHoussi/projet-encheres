package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurService {
    public void creerCompte();
    public void seConnecter();
    public void seDeconneceter();
    public Utilisateur voirProfilUtilisateur(int idUtilisateur);
}
