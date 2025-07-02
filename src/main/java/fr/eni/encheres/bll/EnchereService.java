package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereService {
    void faireEnchere(Enchere enchere, long idArticle);
    void annulerEnchere(long idArticle);
    List<Enchere> consulterEncheres();
    List<Enchere> consulterEnchereparMotCles(String nomArticle);
    List<Enchere> consulterEnchereparCategorie(long idCategorie);
    List<Enchere> consulterEncheresParEtat(String etatVente);


}
