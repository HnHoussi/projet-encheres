package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {
    void create(Enchere enchere, long idArticle);
    List<Enchere> findAll();
    List<Enchere> findByEtat(String etatVente);
    void delete(long idEnchere);
    List<Enchere> findByMotCles(String nomArticle);
    List<Enchere> findByCategorie(long idCategorie);
}
