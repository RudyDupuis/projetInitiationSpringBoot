package fr.eni.tp.filmotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Membre;

@Repository
public class MembreDAOImpl implements MembreDAO {
	
	private static final String SELECT_BY_ID = "SELECT * FROM membre WHERE id = :id";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM membre WHERE email = :email";
	
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Membre read(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new MembreRowMapper());
    }

    @Override
    public Membre read(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("email", email);
        return jdbcTemplate.queryForObject(SELECT_BY_EMAIL, namedParameters, new MembreRowMapper());
    }
    
    public class MembreRowMapper implements RowMapper<Membre> {

        @Override
        public Membre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Membre membre = new Membre();
            membre.setId(rs.getLong("id"));
            membre.setNom(rs.getString("nom"));
            membre.setPrenom(rs.getString("prenom"));
            membre.setPseudo(rs.getString("email"));
            membre.setAdmin(rs.getBoolean("admin"));
            return membre;
        }
    }

}
