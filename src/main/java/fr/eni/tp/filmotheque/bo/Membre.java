package fr.eni.tp.filmotheque.bo;

public class Membre extends Personne {
	public String pseudo;
	public String motDePasse;
	public boolean admin;
	 
	public Membre(long id, String nom, String prenom, String pseudo, String motDePasse) {
		super(id, nom, prenom);
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
	}
	
	public Membre(String nom, String prenom, String pseudo, String motDePasse) {
		super(nom, prenom);
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
	}
	
	public Membre() {
		super();
	}

	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	@Override
	public String toString() {
		return "Membre [pseudo=" + pseudo + ", admin=" + admin + ", id=" + id + ", nom=" + nom + ", prenom=" + prenom
				+ "]";
	}
	
	

}
