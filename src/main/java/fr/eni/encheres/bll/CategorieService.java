package fr.eni.encheres.bll;

import java.util.List;

public interface Categorie {
    public Categorie creerCategorie();
    public void supprimerCategorie(int idUtilisateur);
    public List<Categorie> consulterCategories();

}
