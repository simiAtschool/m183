package com.helvetia.m295.libraryserver.model;

import com.helvetia.m295.libraryserver.common.Ausleihe;
import com.helvetia.m295.libraryserver.service.AusleiheController;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface für DB-Zugang der Entity Ausleihe
 *
 * @author Simon Fäs
 * @version 1.0.0
 * @see Ausleihe
 * @see AusleiheController
 */
public interface AusleiheRepository extends JpaRepository<Ausleihe, Long> {

    /**
     * Methode, um Ausleihe nach der Medium-ID zu suchen
     *
     * @param id
     * @return Liste aller Ausleihen mit der gegebenen Medium-Id
     */
    public List<Ausleihe> findByMediumId(Long id);

    /**
     * Methode, um Ausleihe nach der Medium-ID zu löschen
     *
     * @param id
     */
    public void deleteByMediumId(Long id);

}
