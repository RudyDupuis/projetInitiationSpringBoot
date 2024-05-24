package fr.eni.tp.filmotheque.bo;

public class Genre {
	public long id;
	public String titre;
	
	public Genre(long id, String titre) {
		this.id = id;
		this.titre = titre;
	}
	
	public Genre(String titre) {
		this.titre = titre;
	}

	public Genre() {
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

	@Override
	public String toString() {
		return titre + " (" + id + ")";
	}
	
}
