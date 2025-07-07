package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {

    private CategorieDAO categorieDAO;

    public CategorieServiceImpl(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }


    @Override
    public void creerCategorie(Categorie categorie) {
        categorieDAO.create(categorie);
    }

    @Override
    public void supprimerCategorie(long idCategorie) {
        categorieDAO.delete(idCategorie);
    }

    @Override
    public List<Categorie> consulterCategories() {
        return categorieDAO.findAll();
    }

    @Override
    public void modifierCategorie(Categorie categorie) {
        categorieDAO.update(categorie);
    }

    @Override
    public Categorie consulterCategorieParID(long idCategorie) {
        return categorieDAO.findById(idCategorie);
    }
}
