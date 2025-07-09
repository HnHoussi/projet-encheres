package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {
    public void create(Utilisateur utilisateur);
    public Utilisateur findById(long idUtilisateur);
    public Utilisateur findBypseudo(String pseudo);
    public List<Utilisateur> findAll();
    public void update(Utilisateur utilisateur);
    public void updateActivation(Utilisateur utilisateur);
    public void delete(long idUtilisateur);
}
