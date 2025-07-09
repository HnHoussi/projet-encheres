package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;

import java.util.List;

public interface ArticleDAO {

    List<Article> findArticlesWithDynamicFilters(
            Long idUtilisateur,
            String motCle,
            Long idCategorie,
            String filtrePrincipal,
            List<String> sousFiltres
    );

    //Retourner tous les articles
    List<Article> findAll();

    //Retourner article par ID
    Article findById(long idArticle);

    //Création d'un nouvel article
    void create(Article article);

    // Supprimer un article
    void delete(long  idArticle);



}
