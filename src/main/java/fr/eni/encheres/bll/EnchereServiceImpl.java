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
    public void faireEnchere(Enchere enchere, long idArticle) {
        enchereDAO.create(enchere, idArticle);
    }

    // Supprimer un enchére
    @Override
    public void annulerEnchere(long idArticle, long idUtilisateur, LocalDateTime dateEnchere) {
        enchereDAO.delete(idArticle, idUtilisateur, dateEnchere);
    }

    @Override
    public List<Enchere> consulterMesEncheresEnCours(long idUtilisateur) {
        return enchereDAO.findMesEncheresEnCours(idUtilisateur);
    }

    @Override
    public List<Enchere> consulterMesEncheresEnCoursParMotCles(long idUtilisateur, String motCles) {
        return enchereDAO.findMesEncheresEnCoursParMotCles(idUtilisateur, motCles);
    }

    @Override
    public List<Enchere> consulterMesEncheresEnCoursParCategorie(long idUtilisateur, long idCategorie) {
        return enchereDAO.findMesEncheresEnCoursParCategorie(idUtilisateur, idCategorie);
    }

    @Override
    public List<Enchere> consulterMesEncheresEnCoursParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles) {
        return enchereDAO.findMesEncheresEnCoursParCategorieEtMotCles(idUtilisateur, idCategorie, motCles);
    }

    @Override
    public List<Enchere> consulterMesEncheresRemportees(long idUtilisateur) {
        return enchereDAO.findMesEncheresRemportees(idUtilisateur);
    }

    @Override
    public List<Enchere> consulterMesEncheresRemporteesParMotCles(long idUtilisateur, String motCles) {
        return enchereDAO.findMesEncheresRemporteesParMotCles(idUtilisateur, motCles);
    }

    @Override
    public List<Enchere> consulterMesEncheresRemporteesParCategorie(long idUtilisateur, long idCategorie) {
        return enchereDAO.findMesEncheresRemporteesParCategorie(idUtilisateur, idCategorie);
    }

    @Override
    public List<Enchere> consulterMesEncheresRemporteesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles) {
        return enchereDAO.findMesEncheresRemporteesParCategorieEtMotCles(idUtilisateur, idCategorie, motCles);
    }

}
