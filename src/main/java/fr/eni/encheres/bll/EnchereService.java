package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereService {
    public Enchere faireEnchere(int idArticle);
    public List<Enchere> consulterEncheres();
    public List<Enchere> consulterEncheresParEtat(String etatVente);
    public void annulerEnchere(Enchere enchere);
    public Enchere consulterEnchereparNom(String nomArticle);
    public Enchere consulterEnchereparCategorie(int idEnchere);

}
