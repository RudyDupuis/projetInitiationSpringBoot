package fr.eni.tp.filmotheque.bo;

import jakarta.validation.constraints.*;

public class Avis {
	public long id;
	
	@Min(value = 0)
	@Max(value = 5)
	public int note;
	
	@NotBlank
	@Size(max = 250)
	public String commentaire;
	public Membre membre;
	
	public Avis(long id, int note, String commentaire, Membre membre) {
		this.id = id;
		this.note = note;
		this.commentaire = commentaire;
		this.membre = membre;
	}
	
	public Avis(int note, String commentaire, Membre membre) {
		this.note = note;
		this.commentaire = commentaire;
		this.membre = membre;
	}

	public Avis() {
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	@Override
	public String toString() {
		return "Avis de " + membre.prenom + " " + membre.nom + " (" + id + ") - Membre (pseudo=" + membre.pseudo + ", admin=" + membre.admin + ") - note=" + note + ", commentaire=" + commentaire;
	}
	

}
