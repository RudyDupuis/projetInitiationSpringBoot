package fr.eni.tp.filmotheque.bll;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.AvisDAO;
import fr.eni.tp.filmotheque.dal.FilmDAO;
import fr.eni.tp.filmotheque.dal.GenreDAO;
import fr.eni.tp.filmotheque.dal.MembreDAO;
import fr.eni.tp.filmotheque.dal.ParticipantDAO;
import fr.eni.tp.filmotheque.exceptions.BusinessCode;
import fr.eni.tp.filmotheque.exceptions.BusinessException;

@Service
@Primary
public class FilmServiceImpl implements FilmService {

	private FilmDAO filmDAO;
	private GenreDAO genreDAO;
	private ParticipantDAO participantDAO;
	private AvisDAO avisDAO;
	private MembreDAO membreDAO;

	public FilmServiceImpl(FilmDAO filmDAO, GenreDAO genreDAO, ParticipantDAO participantDAO, AvisDAO avisDAO, MembreDAO membreDAO) {
		this.filmDAO = filmDAO;
		this.genreDAO = genreDAO;
		this.participantDAO = participantDAO;
		this.avisDAO = avisDAO;
		this.membreDAO = membreDAO;
	}

	private Film prepareFilmEntity(Film film) {
		Participant realisateur = participantDAO.read(film.getRealisateur().getId());
		Genre genre = genreDAO.read(film.getGenre().getId());

		film.setGenre(genre);
		film.setRealisateur(realisateur);

		return film;
	}
	
	private Avis prepareAvisEntity(Avis avis) {
		Membre membre = membreDAO.read(avis.getMembre().getId());
		avis.setMembre(membre);
		return avis;
	}

	@Override
	public List<Film> consulterFilms() {
		List<Film> films = filmDAO.findAll();
		
		films.forEach(film -> { film = prepareFilmEntity(film); });
		
		return films;
	}

	@Override
	public Film consulterFilmParId(long id) {
		Film film = prepareFilmEntity(filmDAO.read(id));
		List<Participant> acteurs = participantDAO.findActeurs(id);
		List<Avis> avis = avisDAO.findbyFilm(id);
		
		if(acteurs != null) {
			film.setActeurs(acteurs);	
		}
		
		if(avis != null) {
			avis.forEach(a -> { a = prepareAvisEntity(a); } );
			film.setAvis(avis);
		}
		
		return film;
	}

	@Override
	public List<Genre> consulterGenres() {
		return genreDAO.findAll();
	}

	@Override
	public List<Participant> consulterParticipants() {
		return participantDAO.findAll();
	}

	@Override
	public Genre consulterGenreParId(long id) {
		return genreDAO.read(id);
	}

	@Override
	public Participant consulterParticipantParId(long id) {
		return participantDAO.read(id);
	}

	@Override
	@Transactional
	public void creerFilm(Film film) {
		BusinessException exception = new BusinessException();
		if(isValidFilm(film, exception)) {
			filmDAO.create(film);	
			
			film.getActeurs().forEach(acteur -> {
				participantDAO.create(acteur.getId(), film.getId());
			});
		} else {
			throw exception;
		}
	}

	@Override
	public String consulterTitreFilm(long id) {
		return filmDAO.findTitre(id);
	}

	@Override
	public void publierAvis(Avis avis, long idFilm) {
		avisDAO.create(avis, idFilm);
	}

	@Override
	public List<Avis> consulterAvis(long idFilm) {
		List<Avis> avis = avisDAO.findbyFilm(idFilm);
				
		avis.forEach(a -> { a = prepareAvisEntity(a); } );
		
		return avis;
	}
	
	private boolean isValidFilm(Film film, BusinessException exception) {
		if(film == null) {
			exception.add(BusinessCode.VALIDATION_FILM_NULL);
			return false;
		}
		if(filmDAO.existTitre(film.getTitre())) {
			exception.add(BusinessCode.VALIDATION_FILM_UNIQUE);
			return false;
		}
		return true;
	}

}
