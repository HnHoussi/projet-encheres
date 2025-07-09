package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleDAO  articleDAO;

    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    //faire transaction pour valider nouvel article (nouvelle vente)
    @Override
    public void vendreArticle(Article article) {
        articleDAO.create(article);

    }

    @Override
    public void annulerVente(long idArticle) {
        articleDAO.delete(idArticle);
    }

    @Override
    public List<Article> consulterArticles() {
        return articleDAO.findAll();
    }

    @Override
    public List<Article> consulterArticlesParMotCles(String nomArticle) {
        return articleDAO.findByMotCles(nomArticle);
    }

    @Override
    public List<Article> consulterArticlesParCategorie(long idCategorie) {
        return articleDAO.findByCategorie(idCategorie);
    }

    // Recherche par mots clés et catégorie
    @Override
    public List<Article> consulterArticleParCategorieEtMotCles(long idCategorie, String nomArticle) {
        return articleDAO.findByCategorieEtMotCles(idCategorie, nomArticle);

    }
    
    //gerer etat vente creation nouvelle vente
    public String gererEtatVente(Article article) {
    	System.out.println("gererEtatVente");
    	if(article.getDateDebutEnchere().compareTo(article.getDateFinEnchere()) > 0) {
    		article.setEtatVente("Non débutée");
    	} else
    	if(article.getDateDebutEnchere().compareTo(article.getDateFinEnchere()) == 0) {
    		article.setEtatVente("En cours");
    	} else { 
    		System.out.println("erreur : date de fin antérieur à date de début");
    	}
    	
    	return article.getEtatVente();
    }
    	
}
