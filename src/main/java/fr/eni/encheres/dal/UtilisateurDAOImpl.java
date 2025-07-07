package fr.eni.encheres.dal;

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
public class UtilisateurDAOImpl implements UtilisateurDAO {


    private final String FIND_BY_ID = "SELECT PSEUDO, NOM, PRENOM, EMAIL, TELEPHONE, RUE, CODEPOSTAL, VILLE, MOTDEPASSE, CREDIT, ADMINISTRATEUR, COMPTEACTIF FROM UTILISATEURS WHERE IDUTILISATEUR=:IDUTILISATEUR";
    private final String FIND_ALL = "SELECT PSEUDO, NOM, PRENOM, EMAIL, TELEPHONE, RUE, CODEPOSTAL, VILLE, MOTDEPASSE, CREDIT, ADMINISTRATEUR, COMPTEACTIF FROM UTILISATEURS";
    private final String INSERT = """
        INSERT INTO Utilisateurs(PSEUDO, NOM, PRENOM, EMAIL, TELEPHONE, RUE, CODEPOSTAL, VILLE, MOTDEPASSE, CREDIT, ADMINISTRATEUR, COMPTEACTIF) 
        VALUES (:PSEUDO, :NOM, :PRENOM, :EMAIL, :TELEPHONE, :RUE, :CODEPOSTAL, :VILLE, :MOTDEPASSE, :CREDIT, :ADMINISTRATEUR, :COMPTEACTIF)
        """;
    private static final String UPDATE = """ 
        UPDATE UTILISATEURS SET PSEUDO =:PSEUDO, NOM = :NOM, PRENOM =:PRENOM, TELEPHONE=:TELEPHONE,
             RUE=:RUE, CODEPOSTAL=:CODEPOSTAL, VILLE=:VILLE, MOTDEPASSE=:MOTDEPASSE, CREDIT=:CREDIT, ADMINISTRATEUR=:ADMINISTRATEUR, COMPTEACTIF=:COMPTEACTIF WHERE EMAIL = :EMAIL 
             """;
    private final String UPDATEACTIVATION= "UPDATE UTILISATEURS SET COMPTEACTIF=:COMPTEACTIF WHERE EMAIL = :EMAIL";
    private final String DELETE = "DELETE FROM UTILISATEURS WHERE IDUTILISATEUR = :IDUTILISATEUR";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public UtilisateurDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Utilisateur utilisateur) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("PSEUDO",  utilisateur.getPseudo());
        mapSqlParameterSource.addValue("NOM", utilisateur.getNom());
        mapSqlParameterSource.addValue("PRENOM", utilisateur.getPrenom());
        mapSqlParameterSource.addValue("EMAIL", utilisateur.getEmail());
        mapSqlParameterSource.addValue("TELEPHONE",utilisateur.getTelephone());
        mapSqlParameterSource.addValue("RUE",utilisateur.getRue());
        mapSqlParameterSource.addValue("CODEPOSTAL",utilisateur.getCodePostal());
        mapSqlParameterSource.addValue("VILLE",utilisateur.getVille());
        mapSqlParameterSource.addValue("MOTDEPASSE",utilisateur.getMotDePasse());
        mapSqlParameterSource.addValue("CREDIT",utilisateur.getCredit());
        mapSqlParameterSource.addValue("ADMINISTRATEUR",utilisateur.isAdministrateur());
        mapSqlParameterSource.addValue("COMPTEACTIF",utilisateur.isCompteActif());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT, mapSqlParameterSource, keyHolder);

        if(keyHolder != null && keyHolder.getKey() != null) {
            utilisateur.setIdUtilisateur(keyHolder.getKey().intValue());
        }
    }

    @Override
    public Utilisateur findById(long idUtilisateur) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("IDUTILISATEUR", idUtilisateur);
        return jdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public List<Utilisateur> findAll() {return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));}

    @Override
    public void update(Utilisateur utilisateur) {
        jdbcTemplate.update(UPDATE, new BeanPropertySqlParameterSource(utilisateur));
    }

    @Override
    public void updateActivation(Utilisateur utilisateur) {
        jdbcTemplate.update(UPDATEACTIVATION, new BeanPropertySqlParameterSource(utilisateur));
    }

    @Override
    public void delete(long idUtilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource("IDUTILISATEUR", idUtilisateur);
        jdbcTemplate.update(DELETE, params);

    }

}