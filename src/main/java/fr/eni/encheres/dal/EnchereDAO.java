package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {
    public void create(int idArticle);
    public List<Enchere> findAll();
    public Enchere findByEtat(String etat);
    public void delete(int idEnchere);
    public Enchere findByNom(String nom);
    public List<Enchere> findByCategorie(int idCategorie);
}
