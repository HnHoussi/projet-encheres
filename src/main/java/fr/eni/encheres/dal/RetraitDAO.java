package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retrait;

public interface RetraitDAO {
    public Retrait read(long idArticle);
    public void create(Retrait retrait);
}
