package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;

import java.util.List;

public class ArticleDAOImpl implements ArticleDAO{
    @Override
    public void create(Article article, int idUtilisateur) {

    }

    @Override
    public void delete(int idArticle) {

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
    public Article findByEtat(int idArticle) {
        return null;
    }
}
