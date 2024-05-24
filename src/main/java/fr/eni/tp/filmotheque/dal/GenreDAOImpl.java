package fr.eni.tp.filmotheque.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Genre;

@Repository
public class GenreDAOImpl implements GenreDAO {
	
	private static final String SELECT_BY_ID = "SELECT * FROM genre WHERE id = :id";
	private static final String SELECT_ALL = "SELECT * FROM genre";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Genre read(long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		return jdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new BeanPropertyRowMapper<>(Genre.class));
	}

	@Override
	public List<Genre> findAll() {
		return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Genre.class));
	}

}
