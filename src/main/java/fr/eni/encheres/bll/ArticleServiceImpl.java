package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDAO  articleDAO;

    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }


    // Creation d'article
    @Override
    public void vendreArticle(Article article) {
        articleDAO.create(article);

    }

    @Override
    public void annulerVente(long idArticle) {
        articleDAO.delete(idArticle);
    }

    //Consulter tous les articles
    @Override
    public List<Article> consulterArticles() {
        return articleDAO.findAll();
    }


    // Recherche par mots clés
    @Override
    public List<Article> consulterArticlesParMotCles(String nomArticle) {
        return articleDAO.findByMotCles(nomArticle);
    }

    // Recherche par catégorie
    @Override
    public List<Article> consulterArticlesParCategorie(long idCategorie) {
        return articleDAO.findByCategorie(idCategorie);
    }

    // Recherche par mots clés et catégorie
    @Override
    public List<Article> consulterArticlesParCategorieEtMotCles(long idCategorie, String nomArticle) {
        return articleDAO.findByCategorieEtMotCles(idCategorie, nomArticle);

    }

    // Articles ouverts aux enchères
    @Override
    public List<Article> consulterArticlesOuverts() {
        return articleDAO.findArticlesOuverts();
    }

    // Ventes en cours d'un utilisateur
    @Override
    public List<Article> consulterMesVentesEnCours(long idUtilisateur) {
        return articleDAO.findMesVentesEnCours(idUtilisateur);
    }

    @Override
    public List<Article> consulterMesVentesEnCoursParMotCles(long idUtilisateur, String motCles) {
        return articleDAO.findMesVentesEnCoursParMotCles(idUtilisateur, motCles);
    }

    @Override
    public List<Article> consulterMesVentesEnCoursParCategorie(long idUtilisateur, long idCategorie) {
        return articleDAO.findMesVentesEnCoursParCategorie(idUtilisateur, idCategorie);
    }

    @Override
    public List<Article> consulterMesVentesEnCoursParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles) {
        return articleDAO.findMesVentesEnCoursParCategorieEtMotCles(idUtilisateur, idCategorie, motCles);
    }

    // Ventes non débutées d'un utilisateur
    @Override
    public List<Article> consulterVentesNonDebutees(long idUtilisateur) {
        return articleDAO.findVentesNonDebutees(idUtilisateur);
    }

    @Override
    public List<Article> consulterVentesNonDebuteesParMotCles(long idUtilisateur, String motCles) {
        return articleDAO.findVentesNonDebuteesParMotCles(idUtilisateur, motCles);
    }

    @Override
    public List<Article> consulterVentesNonDebuteesParCategorie(long idUtilisateur, long idCategorie) {
        return articleDAO.findVentesNonDebuteesParCategorie(idUtilisateur, idCategorie);
    }

    @Override
    public List<Article> consulterVentesNonDebuteesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles) {
        return articleDAO.findVentesNonDebuteesParCategorieEtMotCles(idUtilisateur, idCategorie, motCles);
    }

    // Ventes terminées d'un utilisateur
    @Override
    public List<Article> consulterVentesTerminees(long idUtilisateur) {
        return articleDAO.findVentesTerminees(idUtilisateur);
    }

    @Override
    public List<Article> consulterVentesTermineesParMotCles(long idUtilisateur, String motCles) {
        return articleDAO.findVentesTermineesParMotCles(idUtilisateur, motCles);
    }

    @Override
    public List<Article> consulterVentesTermineesParCategorie(long idUtilisateur, long idCategorie) {
        return articleDAO.findVentesTermineesParCategorie(idUtilisateur, idCategorie);
    }

    @Override
    public List<Article> consulterVentesTermineesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles) {
        return articleDAO.findVentesTermineesParCategorieEtMotCles(idUtilisateur, idCategorie, motCles);
    }

    // Recherche parmi les articles ouverts par mots clés
    @Override
    public List<Article> consulterArticlesOuvertsParMotCles(String nomArticle) {

        return articleDAO.findArticlesOuvertsParMotCles(nomArticle);
    }

    // Recherche parmi les articles ouverts par catégorie
    @Override
    public List<Article> consulterArticlesOuvertsParCategorie(long idCategorie) {
        return articleDAO.findArticlesOuvertsParCategorie(idCategorie);
    }

    // Recherche parmi les articles ouverts par catégorie ET mots clés
    @Override
    public List<Article> consulterArticlesOuvertsParCategorieEtMotCles(long idCategorie, String nomArticle) {
        return articleDAO.findArticlesOuvertsParCategorieEtMotCles(idCategorie, nomArticle);
    }


}
