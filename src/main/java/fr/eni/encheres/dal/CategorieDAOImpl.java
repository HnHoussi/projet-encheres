package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieDAOImpl implements CategorieDAO {

    private final String INSERT = "INSERT INTO Categories(libelle) VALUES (:libelle)";
    private final String FIND_BY_ID = "SELECT * FROM CATEGORIES WHERE idCategorie=:idCategorie";
    private final String DELETE = "DELETE FROM Categories WHERE idCategorie = :idCategorie";
    private final String FIND_ALL = "SELECT idCategorie, libelle FROM CATEGORIES";
    private static final String UPDATE = "UPDATE CATEGORIES SET libelle =:libelle WHERE idCategorie = :idCategorie";

    private NamedParameterJdbcTemplate jdbcTemplate;


    public CategorieDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Categorie categorie) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("libelle",  categorie.getLibelle());


        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT, mapSqlParameterSource, keyHolder);

        if(keyHolder != null && keyHolder.getKey() != null) {
            categorie.setIdCategorie(keyHolder.getKey().longValue());
        }

    }

    @Override
    public void delete(long idCategorie) {
        MapSqlParameterSource params = new MapSqlParameterSource("idCategorie", idCategorie);
        jdbcTemplate.update(DELETE, params);
    }

    @Override
    public void update(Categorie categorie) {
        jdbcTemplate.update(UPDATE, new BeanPropertySqlParameterSource(categorie));
    }

    @Override
    public Categorie findById(long idCategorie) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idCategorie", idCategorie);
        return jdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource, new BeanPropertyRowMapper<>(Categorie.class));

    }

    @Override
    public List<Categorie> findAll() {
        return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Categorie.class));
    }
}
