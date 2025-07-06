package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

import java.time.LocalDateTime;
import java.util.List;

public interface EnchereDAO {

    //Retourner tous les enchéres
    List<Enchere> findAll();

    //Retourner les enchéres par mots clés
    List<Enchere> findByMotCles(String nomArticle);

    // Retourner les enchéres par catégorie
    List<Enchere> findByCategorie(long idCategorie);

    //Retourner les enchéres par mots clés et catégories
    List<Enchere> findByCategorieEtMotCles(long idCategorie, String nomArticle);

    // Retourner les enchéres faites par un utilisateur
    List<Enchere> findMesEncheres(long idUtilisateur);

    //Retourner les enchére remportés par un utilisateur
    List<Enchere> findMesEncheresRemportees(long idUtilisateur);

    // Encherir un article
    void create(Enchere enchere, long idArticle);

    //Supprimer un enchére
    void delete(long idEnchere, long idUtilisateur, LocalDateTime dateEnchere);

    //List<Enchere> findByEtat(String etatVente, long idUtilisateur);
}

