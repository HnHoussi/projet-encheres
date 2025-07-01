package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;

import java.util.List;

public interface ArticleDAO {
    void create(Article article, int idUtilisateur);
    void delete(int  idArticle);
    List<Article> findAll();
    List<Article> findByNom(String nomArticle);
    List<Article> findByCategorie(String idCategorie);
    Article findByEtat(int idArticle);

}
