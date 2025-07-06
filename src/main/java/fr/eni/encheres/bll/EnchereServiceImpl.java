package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.EnchereDAO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {

    private final EnchereDAO enchereDAO;

    public EnchereServiceImpl(EnchereDAO enchereDAO) {
        this.enchereDAO = enchereDAO;
    }

    // Encherer sur un article
    @Override
    public void faireEnchere(Enchere enchere,long idArticle) {
        enchereDAO.create(enchere, idArticle);
    }

    // Consulter tous les enchéres
    @Override
    public List<Enchere> consulterEncheres() {
        return enchereDAO.findAll();
    }

    // Supprimer un enchére
    @Override
    public void annulerEnchere(long idArticle, long idUtilisateur, LocalDateTime dateEnchere) {
        enchereDAO.delete(idArticle, idUtilisateur, dateEnchere);
    }

    // Recherche par mot clé
    @Override
    public List<Enchere> consulterEnchereparMotCles(String nomArticle) {
        return enchereDAO.findByMotCles(nomArticle);
    }

    // Recherche par catégorie
    @Override
    public List<Enchere> consulterEnchereparCategorie(long idCategorie) {
        return enchereDAO.findByCategorie(idCategorie);
    }

    // Recherche par mots clés et catégorie
    @Override
    public List<Enchere> consulterEnchereparCategorieEtMotCles(long idCategorie, String nomArticle) {
        return enchereDAO.findByCategorieEtMotCles(idCategorie, nomArticle);

    }

    // Rechercher les enchéres d'un utilisateur
    @Override
    public List<Enchere> consulterMesEncheres(long idUtilisateur) {
        return enchereDAO.findMesEncheres(idUtilisateur);
    }

    //Rechercher les enchéres gagnées par un utilisateur
    @Override
    public List<Enchere> consulterMesEncheresRemportees(long idUtilisateur) {
        return enchereDAO.findMesEncheresRemportees(idUtilisateur);
    }
}
