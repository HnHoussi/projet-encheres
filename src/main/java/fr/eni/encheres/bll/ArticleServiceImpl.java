package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public void vendreArticle(Article article) {

    }

    @Override
    public void annulerVente(int idArticle) {

    }

    @Override
    public List<Article> consulterArticles() {
        return List.of();
    }

    @Override
    public List<Article> consulterArticlesParNom(String nomArticle) {
        return List.of();
    }

    @Override
    public List<Article> consulterArticlesParCategorie(String idCategorie) {
        return List.of();
    }

    @Override
    public List<Article> consulterArticlesParEtat(String etatVente) {
        return List.of();
    }
}
