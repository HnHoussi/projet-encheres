package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findArticlesWithDynamicFilters(
            Long idUtilisateur,
            String motCle,
            Long idCategorie,
            String filtrePrincipal,
            List<String> sousFiltres
    );

    Article consulterArticleById(long idArticle);
    void vendreArticle(Article article);
    void annulerVente(long idArticle);
    Article consulterArticle(long idArticle);
    List<Article> consulterArticles();


}
