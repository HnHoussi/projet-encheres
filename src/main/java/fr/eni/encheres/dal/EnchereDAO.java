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

    // Retourner les enchéres en cours faites par un utilisateur
    List<Enchere> findMesEncheresEnCours(long idUtilisateur);
    List<Enchere> findMesEncheresEnCoursParMotCles(long idUtilisateur, String motCles);
    List<Enchere> findMesEncheresEnCoursParCategorie(long idUtilisateur, long idCategorie);
    List<Enchere> findMesEncheresEnCoursParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles);
    //Retourner les enchére remportés par un utilisateur
    List<Enchere> findMesEncheresRemportees(long idUtilisateur);
    List<Enchere> findMesEncheresRemporteesParMotCles(long idUtilisateur, String motCles);
    List<Enchere> findMesEncheresRemporteesParCategorie(long idUtilisateur, long idCategorie);
    List<Enchere> findMesEncheresRemporteesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles);


    // Encherir un article
    void create(Enchere enchere, long idArticle);

    //Supprimer un enchére
    void delete(long idEnchere, long idUtilisateur, LocalDateTime dateEnchere);

}

