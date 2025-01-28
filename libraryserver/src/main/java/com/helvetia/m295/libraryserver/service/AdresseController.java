package com.helvetia.m295.libraryserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.helvetia.m295.libraryserver.common.Adresse;
import com.helvetia.m295.libraryserver.model.AdresseRepository;
import com.helvetia.m295.libraryserver.model.KundeRepository;

/**
 * Klasse um Serveranfragen rund um die Entity Adresse zu bearbeiten.
 * Es gibt keine POST- und PUT-Methode, da eine Adresse von mehreren Kunden unabhängig voneinander bewohnt werden kann. 
 * Im Falle eines Umzugs wird automatisch die Adressänderung vorgenommen.
 * @version 1.0.0
 * @author Simon Fäs
 * @see Adresse 
 * @see AdresseRepository
 */
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RestController
@RequestMapping(path = "/adresse")
public class AdresseController {
	
	@Autowired
	private AdresseRepository adresseRepository;
	
	@Autowired
	private KundeRepository kundeRepository;
	
	/**
	 * Get-Mapping, um Adressen nach ZIP-Code zu finden
	 * @param zip
	 * @return Response mit allen Adressen, deren ZIP-Code mit dem Parameter entsprechen
	 */
	@GetMapping("/zip/{zip}")
	public @ResponseBody List<Adresse> getAdressenByZip(@PathVariable("zip") String zip) {
		return adresseRepository.findByZip(zip);
	}
	
	/**
	 * Get-Mapping, um Adressen nach Adresse zu finden
	 * @param adresse
	 * @return Response mit allen Adressen, deren Adresse mit dem Parameter entsprechen
	 */
	@GetMapping("/strasse/{adresse}")
	public @ResponseBody List<Adresse> getAdressenByAdresse(@PathVariable("adresse") String adresse) {
		return adresseRepository.findByAdresse(adresse);
	}
	
	/**
	 * Get-Mapping, um alle Adressen zu holen
	 * @return Response mit allen Adressen
	 */
	@GetMapping("")
	public @ResponseBody List<Adresse> getAllAdressen() {
		return adresseRepository.findAll();
	}
	
	/**
	 * Delete-Mapping, um Adresse nach Id zu löschen.
	 * Vor dem Löschen wird kontrolliert, dass keine Referenzen zum Objekt bestehen.
	 * Falls welche bestehen, wird 409(CONFLICT) zurückgeschickt
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteAdresse(@PathVariable("id") Long id) {
		if (kundeRepository.findByAdresseId(id).size() == 0) {
			adresseRepository.deleteById(id);
			return;
		}
		throw new ResponseStatusException(HttpStatus.CONFLICT);
	}
}
