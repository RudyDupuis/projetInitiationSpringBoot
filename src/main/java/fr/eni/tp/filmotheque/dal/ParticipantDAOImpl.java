package fr.eni.tp.filmotheque.dal;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Participant;

@Repository
public class ParticipantDAOImpl implements ParticipantDAO {
	
	private static final String INSERT = "INSERT INTO acteurs ( id_film, id_participant ) VALUES (:idFilm, :idParticipant)";
    private static final String SELECT_BY_ID = "SELECT * FROM participant WHERE id = :id";
    private static final String SELECT_ALL = "SELECT * FROM participant";

	
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(long idParticipant, long idFilm) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idFilm", idFilm);
		namedParameters.addValue("idParticipant", idParticipant);
		jdbcTemplate.update(INSERT, namedParameters);

	}

	@Override
	public Participant read(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		return jdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new BeanPropertyRowMapper<>(Participant.class));
	}

	@Override
	public List<Participant> findAll() {
		return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Participant.class));
	}

	@Override
	public List<Participant> findActeurs(long id) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getJdbcTemplate()).withProcedureName("FindActeurs").returningResultSet("acteurs", new BeanPropertyRowMapper<>(Participant.class));
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("idFilm", id);
		
		Map<String, Object> result = jdbcCall.execute(namedParameters);
		
		if(result.get("acteurs") != null) {
			return (List<Participant>) result.get("acteurs");
		}
		
		return null;
	}

}
