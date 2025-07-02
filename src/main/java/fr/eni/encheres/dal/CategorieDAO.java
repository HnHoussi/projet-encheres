package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;

import java.util.List;

public interface CategorieDAO {
    public void create(Categorie categorie);
    public void delete(long idCategorie);
    public List<Categorie> readAll();
}
