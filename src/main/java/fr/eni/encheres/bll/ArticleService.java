package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

import java.time.LocalDate;
import java.util.List;

public interface ArticleService {
    void vendreArticle(Article article);
    void annulerVente(long idArticle);
    List<Article> consulterArticles();
    List<Article> consulterArticlesParMotCles(String nomArticle);
    List<Article> consulterArticlesParCategorie(long idCategorie);

    // Recherche par mots clés et catégorie
    List<Article> consulterArticleParCategorieEtMotCles(long idCategorie, String nomArticle);
}
