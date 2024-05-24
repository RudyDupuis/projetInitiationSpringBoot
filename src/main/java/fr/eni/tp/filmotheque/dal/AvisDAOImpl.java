package fr.eni.tp.filmotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Membre;

@Repository
public class AvisDAOImpl implements AvisDAO {
	
    private static final String INSERT = "INSERT INTO avis ( note, commentaire, id_membre, id_film ) VALUES ( :note, :commentaire, :idMembre, :idFilm )";
	private static final String FIND_BY_FILM = "SELECT * FROM avis WHERE id_film = :idFilm";
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(Avis avis, long idFilm) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("note", avis.getNote());
        namedParameters.addValue("commentaire", avis.getCommentaire());
        namedParameters.addValue("idMembre", avis.getMembre().getId());
        namedParameters.addValue("idFilm", idFilm);

        jdbcTemplate.update(INSERT, namedParameters);

	}

	@Override
	public List<Avis> findbyFilm(long idFilm) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("idFilm", idFilm);
		return jdbcTemplate.query(FIND_BY_FILM, namedParameters, new AvisRowMapper());
	}
	
	public class AvisRowMapper implements RowMapper<Avis> {
	    @Override
	    public Avis mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Avis avis = new Avis();
	        avis.setId(rs.getLong("id"));
	        avis.setNote(rs.getInt("note"));
	        avis.setCommentaire(rs.getString("commentaire"));
	        
	        Membre membre = new Membre();
	        membre.setId(rs.getLong("id_membre"));
	        avis.setMembre(membre);
	        
	        return avis;
	    }
	}

}
