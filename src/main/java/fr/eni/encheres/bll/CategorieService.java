package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;

import java.util.List;

public interface CategorieService {
    public void creerCategorie(Categorie categorie);
    public void supprimerCategorie(long idCategorie);
    public void modifierCategorie(Categorie categorie);
    public List<Categorie> consulterCategories();

}
