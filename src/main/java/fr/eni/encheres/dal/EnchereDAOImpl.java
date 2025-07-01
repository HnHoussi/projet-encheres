package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;

import java.util.List;

public class EnchereDAOImpl implements EnchereDAO{
    @Override
    public void create(int idArticle) {

    }

    @Override
    public List<Enchere> findAll() {
        return List.of();
    }

    @Override
    public Enchere findByEtat(String etat) {
        return null;
    }

    @Override
    public void delete(int idEnchere) {

    }

    @Override
    public Enchere findByNom(String nom) {
        return null;
    }

    @Override
    public List<Enchere> findByCategorie(int idCategorie) {
        return List.of();
    }
}
