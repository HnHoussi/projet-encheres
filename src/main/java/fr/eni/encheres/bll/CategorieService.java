package fr.eni.encheres.bll;

import java.util.List;

public interface CategorieService {
    public CategorieService creerCategorie();
    public void supprimerCategorie(int idUtilisateur);
    public List<CategorieService> consulterCategories();

}
