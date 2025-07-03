package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO{

    private NamedParameterJdbcTemplate  namedParameterJdbcTemplate;

    public ArticleDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void create(Article article) {
        String INSERT = """
                        INSERT INTO Articles(nomArticle, description, dateDebutEnchere, dateFinEnchere, miseAPrix, prixVente, etatVente, idUtilisateur, idCategorie)
                        VALUES (:nomArticle, :description, :dateDebutEnchere, :dateFinEnchere, :miseAPrix, :prixVente, :etatVente, :idUtilisateur, :idCategorie)
                        """;

        MapSqlParameterSource  parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("nomArticle", article.getNomArticle());
        parameterSource.addValue("description", article.getDescription());
        parameterSource.addValue("dateDebutEncheres", article.getDateDebutEnchere());
        parameterSource.addValue("dateFinEncheres", article.getDateFinEnchere());
        parameterSource.addValue("miseAPrix", article.getMiseAPrix());
        parameterSource.addValue("prixVente", article.getPrixVente());
        parameterSource.addValue("etatVente", article.getEtatVente());
        parameterSource.addValue("idUtilisateur", article.getUtilisateur());
        parameterSource.addValue("idCategorie", article.getCategorie());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, parameterSource, keyHolder);
        if(keyHolder.getKey() != null) {
            article.setIdArticle(keyHolder.getKey().intValue());
        }

    }

    @Override
    public void delete(long idArticle) {
        String DELETE = "DELETE FROM Articles WHERE idArticle = :idArticle";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idArticle", idArticle);
        namedParameterJdbcTemplate.update(DELETE, parameterSource);
    }

    @Override
    public List<Article> findAll() {
        String FIND_ALL = "SELECT * FROM Articles";
        return namedParameterJdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Article.class));
    }

    @Override
    public Article findById(long idArticle) {
        String FIND_BY_ID = "SELECT * FROM Articles WHERE idArticle = :idArticle";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idArticle", idArticle);

        return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, parameterSource, new BeanPropertyRowMapper<>(Article.class));
    }

    @Override
    public List<Article> findByMotCles(String nomArticle) {
        String FIND_BY_MOT_CLES = "SELECT * FROM Articles WHERE nomArticle LIKE :nomArticle";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("nomArticle", "%" + nomArticle + "%");
        return namedParameterJdbcTemplate.query(FIND_BY_MOT_CLES, parameterSource, new BeanPropertyRowMapper<>(Article.class));
    }

    @Override
    public List<Article> findByCategorie(String idCategorie) {
        String FIND_BY_CATEGORIE = "SELECT * FROM Articles WHERE idCategorie = :idCategorie";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idCategorie", idCategorie);
        return namedParameterJdbcTemplate.query(FIND_BY_CATEGORIE, parameterSource, new BeanPropertyRowMapper<>(Article.class));
    }

    @Override
    public List<Article> findByEtat(String etatVente) {
        String FIND_BY_ETAT = "SELECT * FROM Articles WHERE etatVente = :etatVente";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("etatVente", etatVente);
        return namedParameterJdbcTemplate.query(FIND_BY_ETAT, parameterSource, new BeanPropertyRowMapper<>(Article.class));
    }
}
