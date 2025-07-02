package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retrait;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RetraitDAOImpl implements RetraitDAO {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private final String FIND_RETRAIT = "SELECT * FROM RETRAITS WHERE idArticle = :idArticle";

    private final String INSERT = """
        INSERT INTO RETRAITS(RUE, CODEPOSTAL, VILLE, idArticle) 
        VALUES (:rue, :codePostal, :ville, :idArticle)
        """;

    public RetraitDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Retrait read(long idArticle) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idArticle", idArticle);
        return jdbcTemplate.queryForObject(FIND_RETRAIT, mapSqlParameterSource, new BeanPropertyRowMapper<>(Retrait.class));
    }

    @Override
    public void create(Retrait retrait) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("rue", retrait.getRue());
        mapSqlParameterSource.addValue("codePostal", retrait.getCodePostal());
        mapSqlParameterSource.addValue("ville", retrait.getVille());
        mapSqlParameterSource.addValue("idArticle", retrait.getArticle().getIdArticle());

        // Pas besoin de KeyHolder car Retrait n'a pas d'ID propre dans votre modèle
        jdbcTemplate.update(INSERT, mapSqlParameterSource);
    }
}