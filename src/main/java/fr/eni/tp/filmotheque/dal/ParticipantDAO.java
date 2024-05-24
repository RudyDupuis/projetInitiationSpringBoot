package fr.eni.tp.filmotheque.dal;

import java.util.List;

import fr.eni.tp.filmotheque.bo.Participant;


public interface ParticipantDAO {
	void create(long idParticipant, long idFilm);
	Participant read(long id);
	List<Participant> findAll();
	List<Participant> findActeurs(long id);
}
