package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String FIND_BY_ID = """
        SELECT idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville,
               motDePasse, credit, administrateur, compteActif
        FROM UTILISATEURS
        WHERE idUtilisateur = :idUtilisateur
    """;

    private final String FIND_ALL = """
        SELECT idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville,
               motDePasse, credit, administrateur, compteActif
        FROM UTILISATEURS
    """;

    private final String FIND_BY_PSEUDO = """
        SELECT idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville,
               motDePasse, credit, administrateur, compteActif
        FROM UTILISATEURS
        WHERE pseudo = :pseudo
    """;

    private final String INSERT = """
        INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, codePostal, ville,
                                 motDePasse, credit, administrateur, compteActif)
        VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville,
                :motDePasse, :credit, :administrateur, :compteActif)
    """;

    private final String UPDATE = """
        UPDATE UTILISATEURS SET
            nom = :nom, prenom = :prenom, email = :email, telephone = :telephone,
            rue = :rue, codePostal = :codePostal, ville = :ville,
            credit = :credit, administrateur = :administrateur, compteActif = :compteActif
        WHERE pseudo =  :pseudo
    """;

    private final String UPDATE_ACTIVATION = """
        UPDATE UTILISATEURS SET compteActif = :compteActif WHERE email = :email
    """;

    private final String DELETE = """
        DELETE FROM UTILISATEURS WHERE idUtilisateur = :idUtilisateur
    """;

    public UtilisateurDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Utilisateur utilisateur) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(utilisateur);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT, params, keyHolder);

        if (keyHolder.getKey() != null) {
            utilisateur.setIdUtilisateur(keyHolder.getKey().intValue());
        }
    }

    @Override
    public Utilisateur findById(long idUtilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource("idUtilisateur", idUtilisateur);
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID, params, new BeanPropertyRowMapper<>(Utilisateur.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Utilisateur findBypseudo(String pseudo) {
        MapSqlParameterSource params = new MapSqlParameterSource("pseudo", pseudo);
        try {
            return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, params, new BeanPropertyRowMapper<>(Utilisateur.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Utilisateur> findAll() {
        return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public void update(Utilisateur utilisateur) {
        jdbcTemplate.update(UPDATE, new BeanPropertySqlParameterSource(utilisateur));
    }

    @Override
    public void updateActivation(Utilisateur utilisateur) {
        jdbcTemplate.update(UPDATE_ACTIVATION, new BeanPropertySqlParameterSource(utilisateur));
    }

    @Override
    public void delete(long idUtilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource("idUtilisateur", idUtilisateur);
        jdbcTemplate.update(DELETE, params);
    }
}
