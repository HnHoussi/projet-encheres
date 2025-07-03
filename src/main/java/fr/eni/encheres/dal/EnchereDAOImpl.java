package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    @Override
    public List<Enchere> findAll() {
        String FIND_ALL = "SELECT * FROM Encheres";
        return namedParameterJdbcTemplate.query(FIND_ALL, new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    @Override
    public List<Enchere> findByEtat(String etatVente) {
        String FIND_BY_ETAT = """
                              SELECT e.*
                              FROM Encheres e
                              JOIN Articles a 
                              ON e.idArticle = a.idArticle
                              WHERE a.etatVente = :etatVente
                              """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("etatVente", etatVente);
        return namedParameterJdbcTemplate.query(FIND_BY_ETAT, paramSource, new EnchereRowMapper(utilisateurDAO, articleDAO));
    }

    @Override
    public void delete(long idArticle, long idUtilisateur, LocalDateTime dateEnchere) {
        String DELETE = """
                        DELETE FROM Encheres 
                        WHERE idArticle = :idArticle
                        AND idUtilisateur = :idUtilisateur
                        AND dateEnchere = :dateEnchere
                        """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idArticle", idArticle);
        paramSource.addValue("idUtilisateur", idUtilisateur);
        paramSource.addValue("dateEnchere", Timestamp.valueOf(dateEnchere));
        namedParameterJdbcTemplate.update(DELETE, paramSource);

    }

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
