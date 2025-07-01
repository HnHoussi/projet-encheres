package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {
    public void create(Utilisateur utilisateur);
    public Utilisateur read(int idUtilisateur);
    List<Utilisateur> findAll();
}
