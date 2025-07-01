package fr.eni.encheres.bll;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {
    @Override
    public CategorieService creerCategorie() {
        return null;
    }

    @Override
    public void supprimerCategorie(int idUtilisateur) {

    }

    @Override
    public List<CategorieService> consulterCategories() {
        return List.of();
    }
}
