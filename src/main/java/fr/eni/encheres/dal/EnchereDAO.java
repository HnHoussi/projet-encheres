package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {
    public void create(long idArticle);
    public List<Enchere> findAll();
    public Enchere findByEtat(String etat);
    public void delete(long idEnchere);
    public Enchere findByNom(String nom);
    public List<Enchere> findByCategorie(long idCategorie);
}
