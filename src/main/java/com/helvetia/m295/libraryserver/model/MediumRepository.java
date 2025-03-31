package com.helvetia.m295.libraryserver.model;

import com.helvetia.m295.libraryserver.common.Medium;
import com.helvetia.m295.libraryserver.service.MediumController;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface für DB-Zugang der Entity Medium
 *
 * @author Simon Fäs
 * @version 1.0.0
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
