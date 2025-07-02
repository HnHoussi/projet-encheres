package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;

import java.util.List;

public interface CategorieService {
    public Categorie creerCategorie();
    public void supprimerCategorie(int idUtilisateur);
    public List<Categorie> consulterCategories();

}
