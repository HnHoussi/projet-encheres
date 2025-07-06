package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ArticleDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Article article) {
        String INSERT = """
            INSERT INTO Articles(nomArticle, description, dateDebutEnchere, dateFinEnchere,
                                 miseAPrix, prixVente, etatVente, idUtilisateur, idCategorie)
            VALUES (:nomArticle, :description, :dateDebutEnchere, :dateFinEnchere,
                    :miseAPrix, :prixVente, :etatVente, :idUtilisateur, :idCategorie)
        """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nomArticle", article.getNomArticle());
        params.addValue("description", article.getDescription());
        params.addValue("dateDebutEnchere", article.getDateDebutEnchere());
        params.addValue("dateFinEnchere", article.getDateFinEnchere());
        params.addValue("miseAPrix", article.getMiseAPrix());
        params.addValue("prixVente", article.getPrixVente());
        params.addValue("etatVente", article.getEtatVente());
        params.addValue("idUtilisateur", article.getUtilisateur().getIdUtilisateur());
        params.addValue("idCategorie", article.getCategorie().getIdCategorie());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT, params, keyHolder);
        if (keyHolder.getKey() != null) {
            article.setIdArticle(keyHolder.getKey().longValue());
        }
    }

    @Override
    public void delete(long idArticle) {
        String DELETE = "DELETE FROM Articles WHERE idArticle = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", idArticle);
        jdbcTemplate.update(DELETE, params);
    }

    @Override
    public List<Article> findAll() {
        String sql = """
            SELECT a.*, u.idUtilisateur, u.pseudo, c.idCategorie, c.libelle
            FROM Articles a
            JOIN Utilisateurs u ON a.idUtilisateur = u.idUtilisateur
            JOIN Categories c ON a.idCategorie = c.idCategorie
        """;
        return jdbcTemplate.query(sql, new ArticleWithJoinsMapper());
    }

    @Override
    public Article findById(long idArticle) {
        String sql = """
            SELECT a.*, u.idUtilisateur, u.pseudo, c.idCategorie, c.libelle
            FROM Articles a
            JOIN Utilisateurs u ON a.idUtilisateur = u.idUtilisateur
            JOIN Categories c ON a.idCategorie = c.idCategorie
            WHERE a.idArticle = :id
        """;
        MapSqlParameterSource params = new MapSqlParameterSource("id", idArticle);
        return jdbcTemplate.queryForObject(sql, params, new ArticleWithJoinsMapper());
    }

    @Override
    public List<Article> findByMotCles(String nomArticle) {
        String sql = """
            SELECT a.*, u.idUtilisateur, u.pseudo, c.idCategorie, c.libelle
            FROM Articles a
            JOIN Utilisateurs u ON a.idUtilisateur = u.idUtilisateur
            JOIN Categories c ON a.idCategorie = c.idCategorie
            WHERE a.nomArticle LIKE :mot
        """;
        MapSqlParameterSource params = new MapSqlParameterSource("mot", "%" + nomArticle + "%");
        return jdbcTemplate.query(sql, params, new ArticleWithJoinsMapper());
    }

    @Override
    public List<Article> findByCategorie(String idCategorie) {
        String sql = """
            SELECT a.*, u.idUtilisateur, u.pseudo, c.idCategorie, c.libelle
            FROM Articles a
            JOIN Utilisateurs u ON a.idUtilisateur = u.idUtilisateur
            JOIN Categories c ON a.idCategorie = c.idCategorie
            WHERE a.idCategorie = :idCat
        """;
        MapSqlParameterSource params = new MapSqlParameterSource("idCat", idCategorie);
        return jdbcTemplate.query(sql, params, new ArticleWithJoinsMapper());
    }

    @Override
    public List<Article> findByEtat(String etatVente) {
        String sql = """
            SELECT a.*, u.idUtilisateur, u.pseudo, c.idCategorie, c.libelle
            FROM Articles a
            JOIN Utilisateurs u ON a.idUtilisateur = u.idUtilisateur
            JOIN Categories c ON a.idCategorie = c.idCategorie
            WHERE a.etatVente = :etat
        """;
        MapSqlParameterSource params = new MapSqlParameterSource("etat", etatVente);
        return jdbcTemplate.query(sql, params, new ArticleWithJoinsMapper());
    }

    /**
     * Mapper personnalisé pour joindre Article + Utilisateur + Categorie
     */
    private static class ArticleWithJoinsMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Article article = new Article();
            article.setIdArticle(rs.getLong("idArticle"));
            article.setNomArticle(rs.getString("nomArticle"));
            article.setDescription(rs.getString("description"));
            article.setDateDebutEnchere(rs.getDate("dateDebutEnchere").toLocalDate());
            article.setDateFinEnchere(rs.getDate("dateFinEnchere").toLocalDate());
            article.setMiseAPrix(rs.getInt("miseAPrix"));
            article.setPrixVente(rs.getInt("prixVente"));
            article.setEtatVente(rs.getString("etatVente"));

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setIdUtilisateur(rs.getLong("idUtilisateur"));
            utilisateur.setPseudo(rs.getString("pseudo"));
            article.setUtilisateur(utilisateur);

            Categorie categorie = new Categorie();
            categorie.setIdCategorie(rs.getInt("idCategorie"));
            categorie.setLibelle(rs.getString("libelle"));
            article.setCategorie(categorie);

            return article;
        }
    }
}
