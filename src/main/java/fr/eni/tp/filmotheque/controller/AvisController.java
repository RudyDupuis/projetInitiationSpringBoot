package fr.eni.tp.filmotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Membre;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/avis")
@SessionAttributes({ "membreEnSession" })
public class AvisController {

	private final FilmService filmService;

	public AvisController(FilmService filmService) {
		this.filmService = filmService;
	}

	@GetMapping("/creer")
	public String creerAvis(@RequestParam("idFilm") long idFilm, Model model) {
		model.addAttribute("titreFilm", filmService.consulterTitreFilm(idFilm));
		model.addAttribute("idFilm", idFilm);
		model.addAttribute("avis", new Avis());
		return "view-avis-creer";
	}

	@PostMapping("/creer")
	public String creerAvisPost(@RequestParam("idFilm") long idFilm, @Valid @ModelAttribute Avis avis, BindingResult bindingResult,
			@ModelAttribute("membreEnSession") Membre membreEnSession) {
		if (bindingResult.hasErrors()) {
			return "view-avis-creer";
		}
		
		avis.setMembre(membreEnSession);
		filmService.publierAvis(avis, idFilm);
		return "redirect:/films";

	}
}
