package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;

import java.util.List;

public interface ArticleDAO {
    void create(Article article, long idUtilisateur);
    void delete(long  idArticle);
    List<Article> findAll();
    List<Article> findByNom(String nomArticle);
    List<Article> findByCategorie(String idCategorie);
    List<Article> findByEtat(String etatVente);

}
