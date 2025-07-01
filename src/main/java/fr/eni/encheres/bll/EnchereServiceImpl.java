package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {
    @Override
    public Enchere faireEnchere(int idArticle) {
        return null;
    }

    @Override
    public List<Enchere> consulterEncheres() {
        return List.of();
    }

    @Override
    public List<Enchere> consulterEncheresParEtat(String etatVente) {
        return List.of();
    }

    @Override
    public void annulerEnchere(Enchere enchere) {

    }

    @Override
    public Enchere consulterEnchereparNom(String nomArticle) {
        return null;
    }

    @Override
    public Enchere consulterEnchereparCategorie(int idCategorie) {
        return null;
    }
}
