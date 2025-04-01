package com.helvetia.m295.libraryserver.model;

import com.helvetia.m295.libraryserver.common.Kunde;
import com.helvetia.m295.libraryserver.service.KundeController;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface für DB-Zugang der Entity Kunde
 *
 * @author Simon
 * @version 1.0.0
 * @see Kunde
 * @see KundeController
 */
public interface KundeRepository extends JpaRepository<Kunde, Long> {

    /**
     * Methode, um Kunden anhand ihres Nachnamens zu suchen.
     *
     * @param nachname Der Nachname, nach dem gesucht werden soll.
     * @return Eine Liste von Kunden mit dem angegebenen Nachnamen.
     */
    public List<Kunde> findByNachname(String nachname);

    /**
     * Methode, um nach Kunden basierend auf der ID ihrer Adresse zu suchen.
     *
     * @param id Die ID der Adresse, nach der gesucht werden soll.
     * @return Eine Liste von Kunden, die die angegebene Adresse-ID haben.
     */
    public List<Kunde> findByAdresseId(Long id);

    /**
     * Methode, um Kunden anhand ihrer Adresse zu suchen.
     *
     * @param adresse Die Adresse nach der Kunden gesucht werden sollen.
     * @return Eine Liste von Kunden, die die angegebene Adresse haben.
     */
    public List<Kunde> findByAdresseAdresse(String adresse);

}
