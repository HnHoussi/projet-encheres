package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UtilisateurDAO utilisateurDAO;
    private final CategorieDAO categorieDAO;

    @Autowired
    public ArticleDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                          CategorieDAO categorieDAO,
                          UtilisateurDAO utilisateurDAO) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.categorieDAO = categorieDAO;
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    public List<Article> findArticlesWithDynamicFilters(
            Long idUtilisateur,
            String motCle,
            Long idCategorie,
            String filtrePrincipal,
            List<String> sousFiltres) {

        StringBuilder sql = new StringBuilder("SELECT * FROM Articles a WHERE 1=1 ");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (motCle != null && !motCle.isBlank()) {
            sql.append("AND LOWER(a.nomArticle) LIKE LOWER('%' + :motCle + '%') ");
            params.addValue("motCle", motCle);
        }

        if (idCategorie != null) {
            sql.append("AND a.idCategorie = :idCategorie ");
            params.addValue("idCategorie", idCategorie);
        }

        if (idUtilisateur == null) {
            // Not connected: only open auctions
            sql.append("AND CAST(GETDATE() AS DATE) BETWEEN a.dateDebutEnchere AND a.dateFinEnchere ");
        } else {
            if ("achats".equalsIgnoreCase(filtrePrincipal)) {
                if (sousFiltres != null && !sousFiltres.isEmpty()) {
                    List<String> conditions = new ArrayList<>();

                    if (sousFiltres.contains("ouvertes")) {
                        conditions.add("CAST(GETDATE() AS DATE) BETWEEN a.dateDebutEnchere AND a.dateFinEnchere");
                    }
                    if (sousFiltres.contains("mesEncheres")) {
                        conditions.add("EXISTS (SELECT 1 FROM Encheres e WHERE e.idArticle = a.idArticle AND e.idUtilisateur = :idUtilisateur)");
                    }
                    if (sousFiltres.contains("mesEncheresRemportees")) {
                        conditions.add("(a.idUtilisateur <> :idUtilisateur AND CAST(GETDATE() AS DATE) > a.dateFinEnchere)");
                    }

                    if (!conditions.isEmpty()) {
                        sql.append("AND (");
                        sql.append(String.join(" OR ", conditions));
                        sql.append(") ");
                    }
                    params.addValue("idUtilisateur", idUtilisateur);
                } else {
                    // No subfilters: by default, show open auctions
                    sql.append("AND CAST(GETDATE() AS DATE) BETWEEN a.dateDebutEnchere AND a.dateFinEnchere ");
                }
            } else if ("ventes".equalsIgnoreCase(filtrePrincipal)) {
                if (sousFiltres != null && !sousFiltres.isEmpty()) {
                    List<String> conditions = new ArrayList<>();

                    if (sousFiltres.contains("mesVentesEnCours")) {
                        conditions.add("CAST(GETDATE() AS DATE) BETWEEN a.dateDebutEnchere AND a.dateFinEnchere");
                    }
                    if (sousFiltres.contains("ventesNonDebutees")) {
                        conditions.add("CAST(GETDATE() AS DATE) < a.dateDebutEnchere");
                    }
                    if (sousFiltres.contains("ventesTerminees")) {
                        conditions.add("CAST(GETDATE() AS DATE) > a.dateFinEnchere");
                    }

                    if (!conditions.isEmpty()) {
                        sql.append("AND a.idUtilisateur = :idUtilisateur AND (");
                        sql.append(String.join(" OR ", conditions));
                        sql.append(") ");
                    } else {
                        sql.append("AND a.idUtilisateur = :idUtilisateur ");
                    }
                    params.addValue("idUtilisateur", idUtilisateur);
                } else {
                    // No subfilters: show all user's articles
                    sql.append("AND a.idUtilisateur = :idUtilisateur ");
                    params.addValue("idUtilisateur", idUtilisateur);
                }
            }
        }

        return namedParameterJdbcTemplate.query(sql.toString(), params, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }


    @Override
    public List<Article> findAll() {
        String sql = "SELECT * FROM Articles";
        return namedParameterJdbcTemplate.query(sql, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }

    @Override
    public Article findById(long idArticle) {
        String sql = "SELECT * FROM Articles WHERE idArticle = :idArticle";
        MapSqlParameterSource params = new MapSqlParameterSource("idArticle", idArticle);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }

    @Override
    public void create(Article article) {
        String sql = """
                INSERT INTO Articles
                (nomArticle, description, dateDebutEnchere, dateFinEnchere, miseAPrix, prixVente, etatVente, idUtilisateur, idCategorie)
                VALUES
                (:nomArticle, :description, :dateDebutEnchere, :dateFinEnchere, :miseAPrix, :prixVente, :etatVente, :idUtilisateur, :idCategorie)
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
        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        if (keyHolder.getKey() != null) {
            article.setIdArticle(keyHolder.getKey().longValue());
        }
    }

    @Override
    public void delete(long idArticle) {
        String sql = "DELETE FROM Articles WHERE idArticle = :idArticle";
        MapSqlParameterSource params = new MapSqlParameterSource("idArticle", idArticle);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public static class ArticleRowMapper implements RowMapper<Article> {

        private final UtilisateurDAO utilisateurDAO;
        private final CategorieDAO categorieDAO;

        public ArticleRowMapper(UtilisateurDAO utilisateurDAO, CategorieDAO categorieDAO) {
            this.utilisateurDAO = utilisateurDAO;
            this.categorieDAO = categorieDAO;
        }

        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Article article = new Article();
            article.setIdArticle(rs.getLong("idArticle"));
            article.setNomArticle(rs.getString("nomArticle"));
            article.setDescription(rs.getString("description"));
            article.setDateDebutEnchere(rs.getTimestamp("dateDebutEnchere").toLocalDateTime());
            article.setDateFinEnchere(rs.getTimestamp("dateFinEnchere").toLocalDateTime());
            article.setMiseAPrix(rs.getInt("miseAPrix"));
            article.setPrixVente(rs.getInt("prixVente"));
            article.setEtatVente(rs.getString("etatVente"));

            long idUtilisateur = rs.getLong("idUtilisateur");
            long idCategorie = rs.getLong("idCategorie");

            Utilisateur utilisateur = utilisateurDAO.findById(idUtilisateur);
            Categorie categorie = categorieDAO.findById(idCategorie);

            article.setUtilisateur(utilisateur);
            article.setCategorie(categorie);

            return article;
        }
    }
}
