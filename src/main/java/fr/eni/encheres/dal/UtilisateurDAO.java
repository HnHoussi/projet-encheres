package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {
    public void create(Utilisateur utilisateur);
    public Utilisateur read(long idUtilisateur);
    public List<Utilisateur> findAll();
    public void update(Utilisateur utilisateur);
    public void updateActivation(Utilisateur utilisateur);
    public void delete(long idUtilisateur);
}
