package fr.eni.tp.filmotheque.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.exceptions.BusinessException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/films")
@SessionAttributes({ "genresEnSession", "membreEnSession", "participantsEnSession" })
public class FilmController {
	private final FilmService filmService;

	public FilmController(FilmService filmService) {
		this.filmService = filmService;
	}

	@GetMapping("/detail")
	public String afficherUnFilm(@RequestParam("id") int id, Model model) {
		Film film = filmService.consulterFilmParId(id);
		model.addAttribute("film", film);
		return "view-detail-film";
	}

	@GetMapping
	public String afficherFilms(Model model) {
		List<Film> films = filmService.consulterFilms();
		model.addAttribute("films", films);
		return "view-films";

	}

	@ModelAttribute("genresEnSession")
	public List<Genre> chargerGenres() {
		return filmService.consulterGenres();
	}

	@ModelAttribute("participantsEnSession")
	public List<Participant> chargerParticipants() {
		return filmService.consulterParticipants();
	}

	@GetMapping("/creer")
	public String creerFilms(Model model,
			@SessionAttribute(name = "membreEnSession", required = false) Membre membreEnSession) {
		if (membreEnSession == null || !membreEnSession.isAdmin()) {
			return "redirect:/films";
		}

		model.addAttribute("film", new Film());
		return "view-film-creer";
	}

	@PostMapping("/creer")
	public String creerFilmsPost(@Valid @ModelAttribute Film film, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "view-film-creer";
		}

		try {
			filmService.creerFilm(film);
		} catch (BusinessException e) {
			e.getClefsExternalisations().forEach(key -> {
				ObjectError error = new ObjectError("globalError", key);
				bindingResult.addError(error);
			});
			
			return "view-film-creer";
		}

		return "redirect:/films";
	}

}
