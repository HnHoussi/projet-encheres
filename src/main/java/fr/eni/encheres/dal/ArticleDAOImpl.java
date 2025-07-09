package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class ArticleDAOImpl implements ArticleDAO{

    private NamedParameterJdbcTemplate  namedParameterJdbcTemplate;
    private UtilisateurDAO utilisateurDAO;
    private CategorieDAO categorieDAO;

    @Autowired
    public ArticleDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,CategorieDAO categorieDAO, UtilisateurDAO utilisateurDAO) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.categorieDAO = categorieDAO;
        this.utilisateurDAO = utilisateurDAO;
    }

    //Retourner tous les articles
    @Override
    public List<Article> findAll() {
        String FIND_ALL = "SELECT * FROM Articles";
        return namedParameterJdbcTemplate.query(FIND_ALL, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }

    //Retourner les articles par ID
    @Override
    public Article findById(long idArticle) {
        String FIND_BY_ID = "SELECT * FROM Articles WHERE idArticle = :idArticle";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idArticle", idArticle);

        return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, parameterSource, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }

    //Retourner les articles par mots clés
    @Override
    public List<Article> findByMotCles(String nomArticle) {
        String FIND_BY_MOT_CLES = "SELECT * FROM Articles WHERE nomArticle LIKE :nomArticle";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("nomArticle", "%" + nomArticle + "%");
        return namedParameterJdbcTemplate.query(FIND_BY_MOT_CLES, parameterSource, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }

    //Retourner les articles par catégories
    @Override
    public List<Article> findByCategorie(long idCategorie) {
        String FIND_BY_CATEGORIE = "SELECT * FROM Articles WHERE idCategorie = :idCategorie";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idCategorie", idCategorie);
        return namedParameterJdbcTemplate.query(FIND_BY_CATEGORIE, parameterSource, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }

    //Retourner les articles par catégories et mots clés
    @Override
    public List<Article> findByCategorieEtMotCles(long idCategorie, String nomArticle) {
        String FIND_BY_CATEGORIE_ET_MOTS_CLES = """
                        SELECT *
                        FROM Articles
                        WHERE idCategorie = :idCategorie
                        AND nomArticle LIKE :nomArticle
                        """;

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idCategorie", idCategorie);
        paramSource.addValue("nomArticle", "%" + nomArticle + "%");

        return namedParameterJdbcTemplate.query(
                FIND_BY_CATEGORIE_ET_MOTS_CLES,
                paramSource,
                new ArticleRowMapper(utilisateurDAO, categorieDAO)
        );
    }

    //Retourner les articles ouverts a l'enchere
    @Override
    public List<Article> findArticlesOuverts() {
        String FIND_ARTICLES_OUVERTS = """
                                       SELECT *
                                       FROM Articles
                                       WHERE dateFinEnchere > GETDATE()
                                       AND etatVente = 'EN_COURS'
                                       AND dateDebutEnchere <= GETDATE()
                                       """;
        return namedParameterJdbcTemplate.query(FIND_ARTICLES_OUVERTS, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }

    //Retourner les ventes en cours d'un utilisateur
    @Override
    public List<Article> findMesVentesEnCours(long idUtilisateur) {
        String FIND_MES_VENTES_EN_COURS = """
                                          SELECT *
                                          FROM Articles
                                          WHERE idUtilisateur = :idUtilisateur
                                          AND etatVente = 'EN_COURS'
                                          AND dateFinEnchere > GETDATE()
                                          """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idUtilisateur", idUtilisateur);
        return namedParameterJdbcTemplate.query(FIND_MES_VENTES_EN_COURS, paramSource, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }

    //Retourner les ventes non débutées d'un utilisateur
    @Override
    public List<Article> findVentesNonDebutees(long idUtilisateur) {
        String FIND_VENTES_NON_DEBUTEES = """
                                          SELECT *
                                          FROM Articles
                                          WHERE idUtilisateur = :idUtilisateur
                                          AND dateDebutEnchere > GETDATE()
                                          """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idUtilisateur", idUtilisateur);
        return namedParameterJdbcTemplate.query(FIND_VENTES_NON_DEBUTEES, paramSource, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }

    //Retourner les ventes terminées d'un utilisateur
    @Override
    public List<Article> findVentesTerminees(long idUtilisateur) {
        String FIND_VENTES_TERMINEES = """
                                       SELECT *
                                       FROM Articles
                                       WHERE idUtilisateur = :idUtilisateur
                                       AND etatVente = 'TERMINEE'
                                       """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idUtilisateur", idUtilisateur);
        return namedParameterJdbcTemplate.query(FIND_VENTES_TERMINEES, paramSource, new ArticleRowMapper(utilisateurDAO, categorieDAO));
    }


    // création d'un nouvel article
    @Override
    public void create(Article article) {
        String INSERT = """
                        INSERT INTO Articles(nomArticle, description, dateDebutEnchere, dateFinEnchere, miseAPrix, prixVente, etatVente, idUtilisateur, idCategorie)
                        VALUES (:nomArticle, :description, :dateDebutEnchere, :dateFinEnchere, :miseAPrix, :prixVente, :etatVente, :idUtilisateur, :idCategorie)
                        """;
        
        System.out.println("appel de la method create de articleDAO");
        
        MapSqlParameterSource  parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("nomArticle", article.getNomArticle());
        parameterSource.addValue("description", article.getDescription());
        parameterSource.addValue("dateDebutEnchere", article.getDateDebutEnchere());
        parameterSource.addValue("dateFinEnchere", article.getDateFinEnchere());
        parameterSource.addValue("miseAPrix", article.getMiseAPrix());
        parameterSource.addValue("prixVente", article.getPrixVente());
        parameterSource.addValue("etatVente", article.getEtatVente());
        parameterSource.addValue("idUtilisateur", article.getUtilisateur().getIdUtilisateur());
        parameterSource.addValue("idCategorie", article.getCategorie().getIdCategorie());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, parameterSource, keyHolder);
        if(keyHolder.getKey() != null) {
            article.setIdArticle(keyHolder.getKey().intValue());
        }

    }

    //Supprimer un article
    @Override
    public void delete(long idArticle) {
        String DELETE = "DELETE FROM Articles WHERE idArticle = :idArticle";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idArticle", idArticle);
        namedParameterJdbcTemplate.update(DELETE, parameterSource);
    }


    //Row mapper costum
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

            // Fetch the related objects
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
