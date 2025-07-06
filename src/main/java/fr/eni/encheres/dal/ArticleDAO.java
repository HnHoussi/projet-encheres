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

    //Retourner les articles ouverts a l'enchere
    List<Article> findArticlesOuverts();

    //Retourner les ventes en cours d'un utilisateur
    List<Article> findMesVentesEnCours(long idUtilisateur);

    //Retourner les ventes non débutées d'un utilisateur
    List<Article> findVentesNonDebutees(long idUtilisateur);

    //Retourner les ventes terminées d'un utilisateur
    List<Article> findVentesTerminees(long idUtilisateur);

    //Création d'un nouvel article
    void create(Article article);

    // Supprimer un article
    void delete(long  idArticle);



}
