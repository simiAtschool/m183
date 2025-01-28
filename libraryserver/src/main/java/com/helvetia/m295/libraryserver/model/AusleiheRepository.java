package com.helvetia.m295.libraryserver.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helvetia.m295.libraryserver.common.Ausleihe;
import com.helvetia.m295.libraryserver.service.AusleiheController;

/**
 * Interface für DB-Zugang der Entity Ausleihe
 * @version 1.0.0
 * @author Simon Fäs
 * @see Ausleihe 
 * @see AusleiheController
 */
public interface AusleiheRepository extends JpaRepository<Ausleihe, Long> {
	
	/**
	 * Methode, um Ausleihe nach der Medium-ID zu suchen
	 * @param id
	 * @return Liste aller Ausleihen mit der gegebenen Medium-Id
	 */
	public List<Ausleihe> findByMediumId(Long id);
	
	/**
	 * Methode, um Ausleihe nach der Medium-ID zu löschen
	 * @param id
	 */
	public void deleteByMediumId(Long id);
	
}
