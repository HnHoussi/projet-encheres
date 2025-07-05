package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

import java.time.LocalDateTime;
import java.util.List;

public interface EnchereDAO {
    void create(Enchere enchere, long idArticle);
    List<Enchere> findAll();
    List<Enchere> findByEtat(String etatVente);
    void delete(long idEnchere, long idUtilisateur, LocalDateTime dateEnchere);
    List<Enchere> findByMotCles(String nomArticle);
    List<Enchere> findByCategorie(long idCategorie);
    List<Enchere> findByCategorieEtMotCles(long idCategorie, String nomArticle);
}
