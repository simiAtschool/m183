package com.helvetia.m295.libraryserver.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helvetia.m295.libraryserver.common.Medium;
import com.helvetia.m295.libraryserver.service.MediumController;

/**
 * Interface für DB-Zugang der Entity Medium
 * @version 1.0.0
 * @author Simon Fäs
 * @see Medium
 * @see MediumController
 */
public interface MediumRepository extends JpaRepository<Medium, Long> {

	/**
	 * Methode, um nach Medien anhand ihres Titels zu suchen.
	 *
	 * @param titel Der Titel, nach dem gesucht werden soll.
	 * @return Eine Liste von Medien, die den angegebenen Titel enthalten.
	 */
	public List<Medium> findByTitel(String titel);
		
}
