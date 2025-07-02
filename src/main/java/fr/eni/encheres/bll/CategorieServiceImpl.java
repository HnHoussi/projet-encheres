package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {


    @Override
    public Categorie creerCategorie() {
        return null;
    }

    @Override
    public void supprimerCategorie(int idUtilisateur) {

    }

    @Override
    public List<Categorie> consulterCategories() {
        return List.of();
    }
}
