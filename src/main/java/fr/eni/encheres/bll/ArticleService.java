package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;

import java.util.List;

public interface ArticleService {
    void vendreArticle(Article article);
    void annulerVente(long idArticle);

    List<Article> consulterArticles();
    List<Article> consulterArticlesParMotCles(String nomArticle);
    List<Article> consulterArticlesParCategorie(long idCategorie);

    // Recherche par mots clés et catégorie
    List<Article> consulterArticlesParCategorieEtMotCles(long idCategorie, String nomArticle);

    // Articles ouverts aux enchères
    List<Article> consulterArticlesOuverts();
    List<Article> consulterArticlesOuvertsParMotCles(String nomArticle);
    List<Article> consulterArticlesOuvertsParCategorie(long idCategorie);
    List<Article> consulterArticlesOuvertsParCategorieEtMotCles(long idCategorie, String nomArticle);

    // Ventes en cours d'un utilisateur
    List<Article> consulterMesVentesEnCours(long idUtilisateur);
    List<Article> consulterMesVentesEnCoursParMotCles(long idUtilisateur, String motCles);
    List<Article> consulterMesVentesEnCoursParCategorie(long idUtilisateur, long idCategorie);
    List<Article> consulterMesVentesEnCoursParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles);

    // Ventes non débutées d'un utilisateur
    List<Article> consulterVentesNonDebutees(long idUtilisateur);
    List<Article> consulterVentesNonDebuteesParMotCles(long idUtilisateur, String motCles);
    List<Article> consulterVentesNonDebuteesParCategorie(long idUtilisateur, long idCategorie);
    List<Article> consulterVentesNonDebuteesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles);

    // Ventes terminées d'un utilisateur
    List<Article> consulterVentesTerminees(long idUtilisateur);
    List<Article> consulterVentesTermineesParMotCles(long idUtilisateur, String motCles);
    List<Article> consulterVentesTermineesParCategorie(long idUtilisateur, long idCategorie);
    List<Article> consulterVentesTermineesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles);
}
