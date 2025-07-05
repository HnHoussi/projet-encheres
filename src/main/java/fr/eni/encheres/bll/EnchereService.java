package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;

import java.time.LocalDateTime;
import java.util.List;

public interface EnchereService {
    void faireEnchere(Enchere enchere, long idArticle);
    void annulerEnchere(long idArticle, long idUtilisateur, LocalDateTime dateEnchere);
    List<Enchere> consulterEncheres();
    List<Enchere> consulterEnchereparMotCles(String nomArticle);
    List<Enchere> consulterEnchereparCategorie(long idCategorie);
    List<Enchere> consulterEnchereparCategorieEtMotCles(long idCategorie, String nomArticle);
    List<Enchere> consulterEncheresParEtat(String etatVente);


}
