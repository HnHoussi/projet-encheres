package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;

import java.time.LocalDateTime;
import java.util.List;

public interface EnchereService {
    void faireEnchere(Enchere enchere, long idArticle);
    void annulerEnchere(long idArticle, long idUtilisateur, LocalDateTime dateEnchere);


    // Rechercher les enchéres en cours d'un utilisateur
    List<Enchere> consulterMesEncheresEnCours(long idUtilisateur);
    List<Enchere> consulterMesEncheresEnCoursParMotCles(long idUtilisateur, String motCles);
    List<Enchere> consulterMesEncheresEnCoursParCategorie(long idUtilisateur, long idCategorie);
    List<Enchere> consulterMesEncheresEnCoursParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles);

    //Rechercher les enchéres gagnées par un utilisateur
    List<Enchere> consulterMesEncheresRemportees(long idUtilisateur);
    List<Enchere> consulterMesEncheresRemporteesParMotCles(long idUtilisateur, String motCles);
    List<Enchere> consulterMesEncheresRemporteesParCategorie(long idUtilisateur, long idCategorie);
    List<Enchere> consulterMesEncheresRemporteesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles);
}
