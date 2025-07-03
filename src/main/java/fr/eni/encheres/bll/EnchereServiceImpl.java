package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.EnchereDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {

    private EnchereDAO enchereDAO;

    public EnchereServiceImpl(EnchereDAO enchereDAO) {
        this.enchereDAO = enchereDAO;
    }

    @Override
    public void faireEnchere(Enchere enchere,long idArticle) {
        enchereDAO.create(enchere, idArticle);
    }

    @Override
    public List<Enchere> consulterEncheres() {
        return enchereDAO.findAll();
    }

    @Override
    public List<Enchere> consulterEncheresParEtat(String etatVente) {
        return enchereDAO.findByEtat(etatVente);
    }

    @Override
    public void annulerEnchere(long idArticle) {
        enchereDAO.delete(idArticle);

    }

    @Override
    public List<Enchere> consulterEnchereparMotCles(String nomArticle) {
        return enchereDAO.findByMotCles(nomArticle);
    }

    @Override
    public List<Enchere> consulterEnchereparCategorie(long idCategorie) {
        return enchereDAO.findByCategorie(idCategorie);
    }
}
