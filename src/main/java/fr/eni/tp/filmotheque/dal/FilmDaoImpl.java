package fr.eni.tp.filmotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;

@Repository
public class FilmDaoImpl implements FilmDAO {
	
	private static final String INSERT = "INSERT INTO film ( titre, annee, duree, synopsis, id_realisateur, id_genre ) VALUES (:titre, :annee, :duree, :synopsis, :idRealisateur, :idGenre )";
	private static final String SELECT_BY_ID = "SELECT * FROM film WHERE id = :id";
	private static final String COUNT_BY_TITRE = "SELECT COUNT(*) FROM film WHERE titre = :titre";
	private static final String SELECT_ALL = "SELECT * FROM film";
	private static final String SELECT_TITRE_BY_ID = "SELECT titre FROM film WHERE id = :id";
	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(Film film) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    namedParameters.addValue("titre", film.getTitre());
	    namedParameters.addValue("annee", film.getAnnee());
	    namedParameters.addValue("duree", film.getDuree());
	    namedParameters.addValue("synopsis", film.getSynopsis());
	    namedParameters.addValue("idRealisateur", film.getRealisateur().getId());
	    namedParameters.addValue("idGenre", film.getGenre().getId());
		
		jdbcTemplate.update(INSERT, namedParameters, keyHolder);
		
	    Number generatedId = keyHolder.getKey();
	    if (generatedId != null) {
	        film.setId(generatedId.longValue());
	    }
	}

	@Override
	public Film read(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		return jdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new FilmRowMapper());
	}

	@Override
	public List<Film> findAll() {
		return jdbcTemplate.query(SELECT_ALL, new FilmRowMapper());
	}

	@Override
	public String findTitre(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		return jdbcTemplate.queryForObject(SELECT_TITRE_BY_ID, namedParameters, String.class);
	}
	
	class FilmRowMapper implements RowMapper<Film> {

		@Override
		public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
			Film film = new Film();
	        film.setId(rs.getLong("id"));
	        film.setTitre(rs.getString("titre"));
	        film.setAnnee(rs.getInt("annee"));
	        film.setDuree(rs.getInt("duree"));
	        film.setSynopsis(rs.getString("synopsis"));
	        
	        Participant realisateur = new Participant();
	        realisateur.setId(rs.getLong("id_realisateur"));
	        film.setRealisateur(realisateur);
	        
	        Genre genre = new Genre();
	        genre.setId(rs.getLong("id_genre"));
	        film.setGenre(genre);
	        return film;
		}
		
	}

	@Override
	public boolean existTitre(String titre) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("titre", titre);
		Integer count = jdbcTemplate.queryForObject(COUNT_BY_TITRE, namedParameters, Integer.class);
		if(count != 0) {
			return true;
		};
		return false;
	}

}
