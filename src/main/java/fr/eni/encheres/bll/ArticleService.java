package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

import java.time.LocalDate;
import java.util.List;

public interface ArticleService {
    void vendreArticle(Article article);
    void annulerVente(int idArticle);
    List<Article> consulterArticles();
    List<Article> consulterArticlesParNom(String nomArticle);
    List<Article> consulterArticlesParCategorie(String idCategorie);
    List<Article> consulterArticlesParEtat(String etatVente);
}
