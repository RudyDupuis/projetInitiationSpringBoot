package fr.eni.tp.filmotheque.controller.security;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.filmotheque.bll.contexte.ContexteService;
import fr.eni.tp.filmotheque.bo.Membre;

@Controller
@SessionAttributes({ "membreEnSession"})
public class LoginController {
	
	@Autowired
	private ContexteService service;
	
	@GetMapping("/login")
	String login() {
		return "login";
	}
	
	@GetMapping("/session")
	public String chargerMembreEnSession(@ModelAttribute("membreEnSession") Membre membreEnSession, Principal principal) {
		String email = principal.getName();
		Membre aCharger = service.charger(email);
		if( aCharger != null) {
			membreEnSession.setId(aCharger.getId());
			membreEnSession.setNom(aCharger.getNom());
			membreEnSession.setPrenom(aCharger.getPrenom());
			membreEnSession.setPseudo(aCharger.getPseudo());
			membreEnSession.setAdmin(aCharger.isAdmin());
		} else {
			membreEnSession.setId(0);
			membreEnSession.setNom(null);
			membreEnSession.setPrenom(null);
			membreEnSession.setPseudo(null);
			membreEnSession.setAdmin(false);
		}
		return "redirect:/films";
	}
	
	@ModelAttribute("membreEnSession")
	public Membre membreEnSession() {
		return new Membre();
	}

}
