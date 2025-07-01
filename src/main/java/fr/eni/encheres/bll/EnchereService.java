package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereService {
    public Enchere faireEnchere(int idArticle);
    public List<Enchere> listeEncheres();
    public void annulerEnchere(Enchere enchere);

}
