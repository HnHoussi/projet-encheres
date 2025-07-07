package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UtilisateurDAO  utilisateurDAO;
    private final ArticleDAO articleDAO;

    @Autowired
    public EnchereDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UtilisateurDAO utilisateurDAO, ArticleDAO articleDAO) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.utilisateurDAO = utilisateurDAO;
        this.articleDAO = articleDAO;
    }

    // Retourner tous les enchéres
    @Override
    public List<Enchere> findAll() {
        String FIND_ALL = "SELECT * FROM Encheres";
        return namedParameterJdbcTemplate.query(FIND_ALL, new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    // Retourner les enchéres par mots clés
    @Override
    public List<Enchere> findByMotCles(String nomArticle) {
        String FIND_BY_MOT_CLES = """
                                  SELECT e.* 
                                  FROM Encheres e
                                  JOIN Articles a ON e.idArticle = a.idArticle
                                  WHERE a.nomArticle LIKE :nomArticle
                                  """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("nomArticle", "%" + nomArticle + "%");
        return namedParameterJdbcTemplate.query(FIND_BY_MOT_CLES, paramSource, new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    // Retourner les enchéres par catégorie
    @Override
    public List<Enchere> findByCategorie(long idCategorie) {
        String FIND_BY_CATEGORIE = """
                                   SELECT e.* 
                                   FROM Encheres e
                                   JOIN Articles a ON e.idArticle = a.idArticle
                                   WHERE a.idCategorie = :idCategorie
                                   """;
        MapSqlParameterSource  paramSource = new MapSqlParameterSource();
        paramSource.addValue("idCategorie", idCategorie);

        return namedParameterJdbcTemplate.query(FIND_BY_CATEGORIE, paramSource, new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    // Retourner les enchéres par mots clés et catégorie
    @Override
    public List<Enchere> findByCategorieEtMotCles(long idCategorie, String nomArticle) {
        String FIND_BY_CATEGORIE_ET_MOTS_CLES = """
                        SELECT e.*
                        FROM Encheres e
                        JOIN Articles a ON e.idArticle = a.idArticle
                        WHERE a.idCategorie = :idCategorie
                        AND a.nomArticle LIKE :nomArticle
                        """;

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idCategorie", idCategorie);
        paramSource.addValue("nomArticle", "%" + nomArticle + "%");

        return namedParameterJdbcTemplate.query(
                FIND_BY_CATEGORIE_ET_MOTS_CLES,
                paramSource,
                new EnchereRowMapper(utilisateurDAO, articleDAO)
        );
    }

    // Retourner les enchères en cours faites par un utilisateur
    @Override
    public List<Enchere> findMesEncheresEnCours(long idUtilisateur) {
        String sql = """
        SELECT e.*
        FROM Encheres e
        JOIN Articles a ON e.idArticle = a.idArticle
        WHERE e.idUtilisateur = :idUtilisateur
          AND a.dateDebutEnchere <= GETDATE()
          AND a.dateFinEnchere > GETDATE()
    """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUtilisateur", idUtilisateur);
        return namedParameterJdbcTemplate.query(sql, params, new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    @Override
    public List<Enchere> findMesEncheresEnCoursParMotCles(long idUtilisateur, String motCles) {
        String FIND_MES_ENCHERES_EN_COURS_PAR_MOTS_CLES = """
                            SELECT e.*
                            FROM Encheres e
                            JOIN Articles a ON e.idArticle = a.idArticle
                            WHERE e.idUtilisateur = :idUtilisateur
                              AND a.dateDebutEnchere <= GETDATE()
                              AND a.dateFinEnchere > GETDATE()
                              AND a.nomArticle LIKE :motCles
                            """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUtilisateur", idUtilisateur);
        params.addValue("motCles", "%" + motCles + "%");
        return namedParameterJdbcTemplate.query(
                FIND_MES_ENCHERES_EN_COURS_PAR_MOTS_CLES,
                params,
                new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    @Override
    public List<Enchere> findMesEncheresEnCoursParCategorie(long idUtilisateur, long idCategorie) {
        String FIND_MES_ENCHERES_EN_COURS_PAR_CATEGORIE = """
                            SELECT e.*
                            FROM Encheres e
                            JOIN Articles a ON e.idArticle = a.idArticle
                            WHERE e.idUtilisateur = :idUtilisateur
                              AND a.dateDebutEnchere <= GETDATE()
                              AND a.dateFinEnchere > GETDATE()
                              AND a.idCategorie = :idCategorie
                            """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUtilisateur", idUtilisateur);
        params.addValue("idCategorie", idCategorie);
        return namedParameterJdbcTemplate.query(
                FIND_MES_ENCHERES_EN_COURS_PAR_CATEGORIE,
                params,
                new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    @Override
    public List<Enchere> findMesEncheresEnCoursParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles) {
        String FIND_MES_ENCHERES_EN_COURS_PAR_CATEGORIE_ET_MOTS_CLES = """
                        SELECT e.*
                        FROM Encheres e
                        JOIN Articles a ON e.idArticle = a.idArticle
                        WHERE e.idUtilisateur = :idUtilisateur
                          AND a.dateDebutEnchere <= GETDATE()
                          AND a.dateFinEnchere > GETDATE()
                          AND a.idCategorie = :idCategorie
                          AND a.nomArticle LIKE :motCles
                        """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUtilisateur", idUtilisateur);
        params.addValue("idCategorie", idCategorie);
        params.addValue("motCles", "%" + motCles + "%");
        return namedParameterJdbcTemplate.query(
                FIND_MES_ENCHERES_EN_COURS_PAR_CATEGORIE_ET_MOTS_CLES,
                params,
                new EnchereRowMapper(utilisateurDAO, articleDAO));
    }



    //Retourner les enchére remportés par un utilisateur
    @Override
    public List<Enchere> findMesEncheresRemportees(long idUtilisateur) {
        String FIND_MES_ENCHERES_REMPORTEES = """
                                          SELECT e.*
                                          FROM Encheres e
                                          JOIN Articles a ON e.idArticle = a.idArticle
                                          WHERE e.idUtilisateur = :idUtilisateur
                                          AND a.etatVente = 'TERMINEE'
                                          AND e.montantEnchere = a.prixVente
                                          """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idUtilisateur", idUtilisateur);
        return namedParameterJdbcTemplate.query(FIND_MES_ENCHERES_REMPORTEES, paramSource, new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    @Override
    public List<Enchere> findMesEncheresRemporteesParMotCles(long idUtilisateur, String motCles) {
        String FIND_MES_ENCHERES_REMPORTEES_PAR_MOTS_CLES = """
                        SELECT e.*
                        FROM Encheres e
                        JOIN Articles a ON e.idArticle = a.idArticle
                        WHERE e.idUtilisateur = :idUtilisateur
                          AND a.etatVente = 'TERMINEE'
                          AND e.montantEnchere = a.prixVente
                          AND a.nomArticle LIKE :motCles
                        """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUtilisateur", idUtilisateur);
        params.addValue("motCles", "%" + motCles + "%");
        return namedParameterJdbcTemplate.query(
                FIND_MES_ENCHERES_REMPORTEES_PAR_MOTS_CLES,
                params,
                new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    @Override
    public List<Enchere> findMesEncheresRemporteesParCategorie(long idUtilisateur, long idCategorie) {
        String FIND_MES_ENCHERES_REMPORTEES_PAR_CATEGORIE = """
                            SELECT e.*
                            FROM Encheres e
                            JOIN Articles a ON e.idArticle = a.idArticle
                            WHERE e.idUtilisateur = :idUtilisateur
                              AND a.etatVente = 'TERMINEE'
                              AND e.montantEnchere = a.prixVente
                              AND a.idCategorie = :idCategorie
                            """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUtilisateur", idUtilisateur);
        params.addValue("idCategorie", idCategorie);
        return namedParameterJdbcTemplate.query(
                FIND_MES_ENCHERES_REMPORTEES_PAR_CATEGORIE,
                params,
                new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    @Override
    public List<Enchere> findMesEncheresRemporteesParCategorieEtMotCles(long idUtilisateur, long idCategorie, String motCles) {
        String FIND_MES_ENCHERES_REMPORTEES_PAR_CATEGORIE_ET_MOTS_CLES = """
                            SELECT e.*
                            FROM Encheres e
                            JOIN Articles a ON e.idArticle = a.idArticle
                            WHERE e.idUtilisateur = :idUtilisateur
                              AND a.etatVente = 'TERMINEE'
                              AND e.montantEnchere = a.prixVente
                              AND a.idCategorie = :idCategorie
                              AND a.nomArticle LIKE :motCles
                            """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUtilisateur", idUtilisateur);
        params.addValue("idCategorie", idCategorie);
        params.addValue("motCles", "%" + motCles + "%");
        return namedParameterJdbcTemplate.query(
                FIND_MES_ENCHERES_REMPORTEES_PAR_CATEGORIE_ET_MOTS_CLES,
                params,
                new EnchereRowMapper(utilisateurDAO, articleDAO));
    }


    // Création d'un enchére
    @Override
    public void create(Enchere enchere, long idArticle) {
        String INSERT = """
                        INSERT INTO Encheres(dateEnchere, montantEnchere, idUtilisateur, idArticle)
                        VALUES(:dateEnchere, :montantEnchere, :idUtilisateur, :idArticle)
                        """;
        MapSqlParameterSource  paramSource = new MapSqlParameterSource();
        paramSource.addValue("dateEnchere", enchere.getDateEnchere());
        paramSource.addValue("montantEnchere", enchere.getMontantEnchere());
        paramSource.addValue("idArticle", idArticle);
        paramSource.addValue("idUtilisateur", enchere.getUtilisateur().getIdUtilisateur());

        namedParameterJdbcTemplate.update(INSERT, paramSource);

    }

    // supprimer un enchére
    @Override
    public void delete(long idEnchere, long idUtilisateur, LocalDateTime dateEnchere) {
        String DELETE = """
                        DELETE FROM Encheres 
                        WHERE idArticle = :idArticle
                        AND idUtilisateur = :idUtilisateur
                        AND dateEnchere = :dateEnchere
                        """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idEnchere", idEnchere);
        paramSource.addValue("idUtilisateur", idUtilisateur);
        paramSource.addValue("dateEnchere", Timestamp.valueOf(dateEnchere));
        namedParameterJdbcTemplate.update(DELETE, paramSource);

    }


    // RowMapper costum
    public static class EnchereRowMapper implements RowMapper<Enchere> {

        private final UtilisateurDAO  utilisateurDAO;
        private final ArticleDAO articleDAO;

        public EnchereRowMapper(UtilisateurDAO utilisateurDAO, ArticleDAO articleDAO) {
            this.utilisateurDAO = utilisateurDAO;
            this.articleDAO = articleDAO;
        }

        @Override
        public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enchere enchere = new Enchere();
            enchere.setDateEnchere(rs.getTimestamp("dateEnchere").toLocalDateTime());
            enchere.setMontantEnchere(rs.getInt("montantEnchere"));

            long idUtilisateur = rs.getLong("idUtilisateur");
            long idArticle = rs.getLong("idArticle");

            Utilisateur utilisateur = utilisateurDAO.findById(idUtilisateur);
            Article article = articleDAO.findById(idArticle);

            enchere.setUtilisateur(utilisateur);
            enchere.setArticle(article);

            return enchere;
        }
    }
}
