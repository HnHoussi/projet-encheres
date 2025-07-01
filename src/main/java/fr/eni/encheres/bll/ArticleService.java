package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

import java.time.LocalDate;
import java.util.List;

public interface ArticleService {
    List<Article> consulterArticles(int idUtilisateur);
    List<Article> consulterArticlesParNom(String nomArticle);
    List<Article> consulterArticlesParCategorie(String );
    void vendreArticle(Article article);
    void annulerVente(int idArticle);
}
