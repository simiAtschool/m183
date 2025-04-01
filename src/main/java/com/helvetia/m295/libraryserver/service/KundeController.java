package com.helvetia.m295.libraryserver.service;

import com.helvetia.m295.libraryserver.common.Adresse;
import com.helvetia.m295.libraryserver.common.Kunde;
import com.helvetia.m295.libraryserver.model.AdresseRepository;
import com.helvetia.m295.libraryserver.model.KundeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Klasse um Serveranfragen rund um die Entity Kunde zu bearbeiten.
 *
 * @author Simon
 * @version 1.0.0
 * @see Kunde
 * @see KundeRepository
 */
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RestController
@RequestMapping(path = "/kunde")
public class KundeController {

    @Autowired
    private KundeRepository kundeRepository;

    @Autowired
    private AdresseRepository adresseRepository;

    /**
     * Gibt einen Kunden basierend auf der angegebenen ID zurück.
     *
     * @param id Die ID des Kunden, der abgerufen werden soll.
     * @return Der Kunde, der der angegebenen ID entspricht.
     * @throws ResponseStatusException Wenn nichts gefunden wurde.
     */
    @GetMapping("/{id}")
    public @ResponseBody Kunde getKundeById(@PathVariable("id") Long id) {
        try {
            return kundeRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gibt eine Liste von Kunden basierend auf dem angegebenen Nachnamen zurück.
     *
     * @param nachname Der Nachname, nach dem Kunden gesucht werden sollen.
     * @return Eine Liste von Kunden mit dem angegebenen Nachnamen.
     */
    @GetMapping("/nachname/{nachname}")
    public @ResponseBody List<Kunde> getKundeByNachname(@PathVariable("nachname") String nachname) {
        return kundeRepository.findByNachname(nachname);
    }

    /**
     * Gibt eine Liste von Kunden zurück, die mit der angegebenen Adresse übereinstimmen.
     *
     * @param adresse Die Adresse, nach der Kunden gesucht werden sollen.
     * @return Die Liste von Kunden, die mit der angegebenen Adresse übereinstimmen.
     */
    @GetMapping("/adresse/{adresse}")
    public @ResponseBody List<Kunde> getKundeByAdresse(@PathVariable String adresse) {
        return kundeRepository.findByAdresseAdresse(adresse);
    }

    /**
     * Fügt einen neuen Kunden hinzu.
     * Dabei wird das Hinzufügen wird das von {@link #supportAddKunde(Kunde)} übernommen
     *
     * @param data Die Kundendaten, die hinzugefügt werden sollen.
     * @return Der hinzugefügte Kunde.
     */
    @PostMapping("")
    public @ResponseBody Kunde addKunde(@Valid @RequestBody Kunde data) {
        return supportAddKunde(data);
    }

    /**
     * Aktualisiert einen vorhandenen Kunden basierend auf der angegebenen ID.
     * Wenn ein neuer Kunde hinzugefügt wird, dann wird das von {@link #supportAddKunde(Kunde)} übernommen
     *
     * @param data Die aktualisierten Kundendaten.
     * @param id   Die ID des Kunden, der aktualisiert werden soll.
     * @return Der aktualisierte Kunde.
     */
    @PutMapping("/{id}")
    public @ResponseBody Kunde updateKunde(@Valid @RequestBody Kunde data, @PathVariable("id") Long id) {

        var original = data;
        var kunde = kundeRepository.findById(id);

        if (kunde.isPresent()) {
            original = kunde.get();
            if (!original.getAdresse().equals(data.getAdresse()) && data.getAdresse() != null) {
                var list = adresseRepository.findByAdresseAndZip(data.getAdresse().getAdresse(), data.getAdresse().getZip());
                if (list.isEmpty()) {
                    original.setAdresse(new Adresse(0L, data.getAdresse().getAdresse(), data.getAdresse().getOrt(), data.getAdresse().getZip()));
                } else {
                    original.setAdresse(list.getFirst());
                }
            }
            if (data.getEmail() != null)
                original.setEmail(data.getEmail());

            original.setAdresse(adresseRepository.save(original.getAdresse()));
            return kundeRepository.save(original);
        } else {
            return supportAddKunde(data);
        }

    }

    /**
     * Verwaltet das Hinzufügen eines Kunden.
     *
     * @param data Die Kundendaten, die hinzugefügt werden sollen.
     * @return Der hinzugefügte Kunde.
     * @throws ResponseStatusException Wenn die gesendeten Daten unvollständig sind.
     */
    private Kunde supportAddKunde(Kunde data) {
        if (data.getAdresse().getAdresse() == null || data.getAdresse().getOrt() == null
                || data.getAdresse().getZip() == null || data.getEmail() == null || data.getVorname() == null
                || data.getNachname() == null || data.getGeburtstag() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sent data is incomplete");
        }
        var adresse = adresseRepository.findByAdresseAndZip(data.getAdresse().getAdresse(), data.getAdresse().getZip());
        if (adresse.isEmpty()) {
            data.setAdresse(adresseRepository.save(data.getAdresse()));
        } else {
            data.setAdresse(adresse.getFirst());
        }
        return kundeRepository.save(data);
    }

    /**
     * Löscht einen Kunden anhand seiner ID.
     *
     * @param id Die ID des zu löschenden Kunden.
     */
    @DeleteMapping("/{id}")
    public @ResponseBody void deleteKunde(@PathVariable Long id) {
        kundeRepository.deleteById(id);
    }

}
