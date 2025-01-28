package com.helvetia.m295.libraryserver.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.helvetia.m295.libraryserver.common.Medium;
import com.helvetia.m295.libraryserver.model.MediumRepository;

/**
 * Klasse um Serveranfragen rund um die Entity Medium zu bearbeiten.
 * 
 * @version 1.0.0
 * @author Simon Fäs
 * @see Medium
 * @see MediumRepository
 */
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RestController
@RequestMapping(path = "/medium")
public class MediumController {

	@Autowired
	private MediumRepository mediumRepository;

	/**
	 * Gibt ein Medium basierend auf der angegebenen ID zurück.
	 *
	 * @param id Die ID des Mediums, das abgerufen werden soll.
	 * @return Das Medium, das der angegebenen ID entspricht.
	 * @throws ResponseStatusException Wenn nichts gefunden wurde.
	 */
	@GetMapping("/{id}")
	public @ResponseBody Medium getMediumById(@PathVariable("id") Long id) {
		try {
			return mediumRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Gibt eine Liste von Medien basierend auf dem angegebenen Titel zurück.
	 *
	 * @param titel Der Titel, nach dem Medien gesucht werden sollen.
	 * @return Eine Liste von Medien mit dem angegebenen Titel.
	 */
	@GetMapping("/titel/{titel}")
	public @ResponseBody List<Medium> getMedienByTitel(@PathVariable("titel") String titel) {
		return mediumRepository.findByTitel(titel);
	}

	/**
	 * Gibt alle Medien zurück.
	 *
	 * @return Eine Liste aller Medien.
	 */
	@GetMapping("")
	public @ResponseBody List<Medium> getAllMedien() {
		return mediumRepository.findAll();
	}

	/**
	 * Fügt ein neues Medium hinzu.
	 * Dabei wird das Hinzufügen von {@link #supportAddMedium(Medium)} übernommen
	 *
	 * @param data Die Daten des Mediums, das hinzugefügt werden soll.
	 * @return Das hinzugefügte Medium.
	 */
	@PostMapping("")
	public @ResponseBody Medium addMedium(@RequestBody Medium data) {
		return supportAddMedium(data);
	}

	/**
	 * Aktualisiert ein Medium anhand seiner ID.
	 * Wenn ein neues Medium hinzugefügt wird, dann wird das von {@link #supportAddMedium(Medium)} übernommen
	 *
	 * @param data Die aktualisierten Daten des Mediums.
	 * @param id Die ID des zu aktualisierenden Mediums.
	 * @return Das aktualisierte Medium.
	 */
	@PutMapping("/{id}")
	public @ResponseBody Medium updateMedium(@RequestBody Medium data, @PathVariable Long id) {
		
		var medium = mediumRepository.findById(id);
		var original = data;
		
		if (medium.isPresent()) {
			original = medium.get();
			Object value = data.getGenre();
			if (value != null)
				original.setGenre((String) value);
			value = data.getAltersfreigabe();
			if (value != null)
				original.setAltersfreigabe((Short) value);
			value = data.getIsbn();
			if (value != null)
				original.setIsbn((Long) value);
			value = data.getStandortcode();
			if (value != null)
				original.setStandortcode((String) value);
			return mediumRepository.save(original);
		} else {
			return supportAddMedium(data);
		}

	}
	
	/**
	 * Methode, um ein Medium zu sichern
	 * 
	 * @param data Die Daten des Mediums, das hinzugefügt werden soll.
	 * @return Das hinzugefügte Medium.
	 * @throws ResponseStatusException Wenn die gesendeten Daten unvollständig sind.
	 */
	private Medium supportAddMedium(Medium data) {
		if(data.getAutor() == null || data.getTitel() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Sent data is incomplete");
		}
		return mediumRepository.save(data);
	}
	
	/**
	 * Löscht ein Medium anhand seiner ID.
	 *
	 * @param id Die ID des zu löschenden Mediums.
	 */
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteMedium(@PathVariable("id") Long id) {
		mediumRepository.deleteById(id);
	}

}
