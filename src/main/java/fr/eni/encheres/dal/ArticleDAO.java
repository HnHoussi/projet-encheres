package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

import java.util.List;

public interface ArticleDAO {

    //Retourner tous les articles
    List<Article> findAll();

    //Retourner article par ID
    Article findById(long idArticle);

    //Retourner article par mots clés
    List<Article> findByMotCles(String nomArticle);

    //Retourner articles par catégorie
    List<Article> findByCategorie(long idCategorie);

    //Retourner les articles par mots clés et catégories
    List<Article> findByCategorieEtMotCles(long idCategorie, String nomArticle);

    //Retourner les articles ouverts a l'enchere pour tous le monde
    List<Article> findArticlesOuverts();

    // Retourner articles ouvertes par mots clés pour tous le monde
    List<Article> findArticlesOuvertsParMotCles(String nomArticle);

    // Retourner articles ouvertes par catégorie pour tous le monde
    List<Article> findArticlesOuvertsParCategorie(long idCategorie);

    // Retourner articles ouvertes par catégorie et mots clés pour tous le monde
    List<Article> findArticlesOuvertsParCategorieEtMotCles(long idCategorie, String nomArticle);

    //Retourner les ventes en cours d'un utilisateur
    List<Article> findMesVentesEnCours(long idUtilisateur);
    List<Article> findMesVentesEnCoursParMotCles(long idUtilisateur, String nomArticle);
    List<Article> findMesVentesEnCoursParCategorie(long idUtilisateur, long idCategorie);
    List<Article> findMesVentesEnCoursParCategorieEtMotCles(long idUtilisateur, long idCategorie, String nomArticle);

    //Retourner les ventes non débutées d'un utilisateur
    List<Article> findVentesNonDebutees(long idUtilisateur);
    List<Article> findVentesNonDebuteesParMotCles(long idUtilisateur, String nomArticle);
    List<Article> findVentesNonDebuteesParCategorie(long idUtilisateur, long idCategorie);
    List<Article> findVentesNonDebuteesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String nomArticle);

    //Retourner les ventes terminées d'un utilisateur
    List<Article> findVentesTerminees(long idUtilisateur);
    List<Article> findVentesTermineesParMotCles(long idUtilisateur, String nomArticle);
    List<Article> findVentesTermineesParCategorie(long idUtilisateur, long idCategorie);
    List<Article> findVentesTermineesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String nomArticle);

    //Création d'un nouvel article
    void create(Article article);

    // Supprimer un article
    void delete(long  idArticle);



}
