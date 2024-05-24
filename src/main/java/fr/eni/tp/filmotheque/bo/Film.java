package fr.eni.tp.filmotheque.bo;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.*;

public class Film {
	public long id;
	
	@NotBlank
	@Size(max = 250)
	public String titre;
	
	@Min(value = 1900)
	public int annee;
	
	@Min(value = 1)
	public int duree;
	
	@NotBlank
	@Size(min = 20, max = 250)
	public String synopsis;
	
	@NotNull
	public Participant realisateur;
	
	@NotNull
	public List<Participant> acteurs = new ArrayList<>();
	
	public List<Avis> avis = new ArrayList<>();
	
	@NotNull
	public Genre genre;
	
	public Film(long id, String titre, int annee, int duree, String synopsis) {
		this.id = id;
		this.titre = titre;
		this.annee = annee;
		this.duree = duree;
		this.synopsis = synopsis;
	}
	
	public Film(String titre, int annee, int duree, String synopsis) {
		this.titre = titre;
		this.annee = annee;
		this.duree = duree;
		this.synopsis = synopsis;
	}

	public Film() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Participant getRealisateur() {
		return realisateur;
	}

	public void setRealisateur(Participant realisateur) {
		this.realisateur = realisateur;
	}

	public List<Participant> getActeurs() {
		return acteurs;
	}

	public void setActeurs(List<Participant> acteurs) {
		this.acteurs = acteurs;
	}

	public List<Avis> getAvis() {
		return avis;
	}

	public void setAvis(List<Avis> avis) {
		this.avis = avis;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "Film (" + id + ")\n Titre : " + titre + "[annee : " + annee + ", duree : " + duree + " minutes]\n Synopsis : " + synopsis + "\n Realisateur : " + realisateur + "\n Acteurs : " + acteurs + "\n Genre : " + genre + "\n Avis : " + avis + "\n\n"; 
	}
	
}
