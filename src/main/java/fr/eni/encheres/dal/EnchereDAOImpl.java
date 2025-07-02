package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
        paramSource.addValue("idUtilisateur", enchere.getUtilisateur());

        namedParameterJdbcTemplate.update(INSERT, paramSource);

    }

    @Override
    public List<Enchere> findAll() {
        String FIND_ALL = "SELECT * FROM Encheres";
        return namedParameterJdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public List<Enchere> findByEtat(String etatVente) {
        String FIND_BY_ETAT = "SELECT * FROM Encheres WHERE etatVente = :etatVente";
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("etatVente", etatVente);
        return namedParameterJdbcTemplate.query(FIND_BY_ETAT, paramSource, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public void delete(long idArticle) {
        String DELETE = "DELETE FROM Encheres WHERE idArticle = :idArticle";
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idArticle", idArticle);
        namedParameterJdbcTemplate.update(DELETE, paramSource);

    }

    @Override
    public List<Enchere> findByMotCles(String nomArticle) {
        String FIND_BY_MOT_CLES = "SELECT * FROM Encheres WHERE nomArticle LIKE :nomArticle";
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("nomArticle", "%" + nomArticle + "%");
        return namedParameterJdbcTemplate.query(FIND_BY_MOT_CLES, paramSource, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public List<Enchere> findByCategorie(long idCategorie) {
        String FIND_BY_CATEGORIE = "SELECT * FROM Encheres WHERE idCategorie = :idCategorie";
        MapSqlParameterSource  paramSource = new MapSqlParameterSource();
        paramSource.addValue("idCategorie", idCategorie);

        return namedParameterJdbcTemplate.query(FIND_BY_CATEGORIE, paramSource, new BeanPropertyRowMapper<>(Enchere.class));
    }
}
