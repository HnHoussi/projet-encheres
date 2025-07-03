package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;

import java.util.List;

public interface ArticleDAO {
    void create(Article article);
    void delete(long  idArticle);
    List<Article> findAll();
    Article findById(long idArticle);
    List<Article> findByMotCles(String nomArticle);
    List<Article> findByCategorie(String idCategorie);
    List<Article> findByEtat(String etatVente);

}
