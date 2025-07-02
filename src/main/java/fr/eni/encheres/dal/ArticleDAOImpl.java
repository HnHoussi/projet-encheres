package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;

import java.util.List;

public class ArticleDAOImpl implements ArticleDAO{

    @Override
    public void create(Article article, long idUtilisateur) {

    }

    @Override
    public void delete(long idArticle) {

    }

    @Override
    public List<Article> findAll() {
        return List.of();
    }

    @Override
    public List<Article> findByNom(String nomArticle) {
        return List.of();
    }

    @Override
    public List<Article> findByCategorie(String idCategorie) {
        return List.of();
    }

    @Override
    public Article findByEtat(long idArticle) {
        return null;
    }
}
