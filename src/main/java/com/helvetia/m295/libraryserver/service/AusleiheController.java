package com.helvetia.m295.libraryserver.service;

import com.helvetia.m295.libraryserver.common.Ausleihe;
import com.helvetia.m295.libraryserver.model.AusleiheRepository;
import com.helvetia.m295.libraryserver.model.KundeRepository;
import com.helvetia.m295.libraryserver.model.MediumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

/**
 * Klasse um Serveranfragen rund um die Entity Ausleihe zu bearbeiten.
 *
 * @author Simon Fäs
 * @version 1.0.0
 * @see Ausleihe
 * @see AusleiheRepository
 */
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RestController
@RequestMapping(path = "/ausleihe")
public class AusleiheController {

    @Autowired
    private AusleiheRepository ausleiheRepository;

    @Autowired
    private KundeRepository kundeRepository;

    @Autowired
    private MediumRepository mediumRepository;

    /**
     * Gibt Ausleihen basierend auf der angegebenen Medien-ID zurück.
     *
     * @param id Die ID der Ausleihe, die abgerufen werden soll.
     * @return Die Ausleihe, die der angegebenen ID entspricht.
     * @throws ResponseStatusException Wenn nichts gefunden wurde.
     */
    @GetMapping("/{id}")
    public @ResponseBody List<Ausleihe> getAusleiheById(@PathVariable("id") Long id) {
        return ausleiheRepository.findByMediumId(id);
    }

    /**
     * Gibt alle Ausleihen zurück.
     *
     * @return Die Ausleihe, die der angegebenen ID entspricht.
     */
    @GetMapping("")
    public @ResponseBody List<Ausleihe> getAusleihen() {
        return ausleiheRepository.findAll();
    }

    /**
     * Post-Mapping, um neue Ausleihe hinzuzufügen. Das Speichern wird von
     * {@link #supportAddNewAusleihe(Ausleihe)} übernommen
     *
     * @param data Ausleihe, die zu speichern ist
     * @return Ausleihe Gespeicherte Ausleihe
     */
    @PostMapping("")
    public @ResponseBody Ausleihe addNewAusleihe(@RequestBody Ausleihe data) {
        return supportAddNewAusleihe(data);
    }

    /**
     * Put-Mapping, um Ausleihen zu updaten. Das Speichern neuer Ausleihen wird von
     * {@link #supportAddNewAusleihe(Ausleihe)} übernommen
     *
     * @param data Ausleihe, die modifiziert wurde
     * @param id   Id des zu aktualisierenden Elements
     * @return Ausleihe Aktualisierte Ausleihe
     */
    @PutMapping("/{id}")
    public @ResponseBody Ausleihe updateAusleihe(@RequestBody Ausleihe data, @PathVariable("id") Long id) {

        var original = data;
        var ausleihe = ausleiheRepository.findById(id);

        if (ausleihe.isPresent()) {
            original = ausleihe.get();
            original.setAusleihedauer(original.getAusleihedauer() + 14);
            return ausleiheRepository.save(original);
        } else {
            return supportAddNewAusleihe(data);
        }

    }

    /**
     * Fügt Ausleihe hinzu und handhabt Fehler, welche entstehten können.
     *
     * @param data Ausleihe, die gespeichert werden sollte
     * @return Ausleihe Gespeicherte Ausleihe
     */
    private Ausleihe supportAddNewAusleihe(Ausleihe data) {
        try {
            var medium = mediumRepository.findById(data.getMedium().getId());
            var kunde = kundeRepository.findById(data.getKunde().getId());
            var ausleihe = ausleiheRepository.findByMediumId(data.getMedium().getId());
            // Überprüft, ob Medium und Kunde existieren und kontrolliert, ob es bereits
            // eine Ausleihe mit dem Medium gibt
            if (medium.isPresent() && kunde.isPresent() && ausleihe.isEmpty()) {
                // Erstellung der Ausleihe
                data.setKunde(kunde.get());
                data.setMedium(medium.get());
                data.setAusleihedatum(new Date());
                data.setAusleihedauer(14L);
                return ausleiheRepository.save(data);
            } else if (ausleihe.isEmpty()) {
                // Wenn Medium oder Kunde nicht existieren, dann wird 404 zurückgegeben
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else {
                // Wenn es bereits eine Ausleihe mit dem Medium gibt, dann wird 409
                // zurückgegeben
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sent data is incomplete");
        }

    }

    /**
     * Delete-Mapping für Ausleihe. Löscht Ausleihe mithilfe der Medium-ID
     *
     * @param id Medium-Id
     */
    @DeleteMapping("/{id}")
    public @ResponseBody void deleteAusleihe(@PathVariable Long id) {
        ausleiheRepository.deleteByMediumId(id);
    }

}
