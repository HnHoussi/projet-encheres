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

    @Override
    public List<Article> findArticlesWithDynamicFilters(
            Long idUtilisateur,
            String motCle,
            Long idCategorie,
            String filtrePrincipal,
            List<String> sousFiltres) {

        return articleDAO.findArticlesWithDynamicFilters(
                idUtilisateur,
                motCle,
                idCategorie,
                filtrePrincipal,
                sousFiltres
        );
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

}
