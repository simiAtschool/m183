package com.helvetia.m295.libraryserver.model;

import com.helvetia.m295.libraryserver.common.Adresse;
import com.helvetia.m295.libraryserver.service.AdresseController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface für DB-Zugang der Entity Adresse
 *
 * @author Simon
 * @version 1.0.0
 * @see Adresse
 * @see AdresseController
 */
public interface AdresseRepository extends JpaRepository<Adresse, Long> {

    /**
     * Methode, um Adressen nach ZIP zu suchen
     *
     * @param zip
     * @return Resultat aus Datenbankabfrage
     */
    public List<Adresse> findByZip(String zip);

    /**
     * Methode, um Adressen nach Adresse zu suchen.
     * Es muss nur der Anfang der Adresse eingegeben werden, um die gewünschte Adresse zu finden.
     *
     * @param adresse
     * @return Resultat aus Datenbankabfrage
     */
    @Query("SELECT a FROM Adresse a WHERE a.adresse like concat(:adresse, '%')")
    public List<Adresse> findByAdresse(@Param("adresse") String adresse);

    /**
     * Methode, um Adressen nach Adresse und ZIP zu suchen
     *
     * @param adresse
     * @param zip
     * @return Resultat aus Datenbankabfrage
     */
    @Query("SELECT a FROM Adresse a WHERE a.adresse like :adresse AND a.zip like :zip")
    public List<Adresse> findByAdresseAndZip(@Param("adresse") String adresse, @Param("zip") String zip);

}
