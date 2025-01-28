package com.helvetia.libraryapp.common.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Objects;

/**
 * DTO-Klasse für die DB-Entity Adresse <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>id: Eindeutiges Attribut der Adresse</li>
 * <li>adresse: Strassenname und Hausnummer der Adresse eines Kunden</li>
 * <li>ort: Ort der Adresse eines Kunden</li>
 * <li>zip: ZIP-Code der Adresse eines Kunden</li>
 * </ul>
 * 
 * @version 1.0.0
 * @author Simon Fäs
 */
@JsonInclude(Include.NON_NULL)
public class Adresse extends BaseItem {
	private Long id;
	private String adresse;
	private String ort;
	private String zip;
	
	/**
	 * Standard constructor
	 */
	public Adresse() {}
	
	/**
	 * Constructor mit allen Attributen als Parameter
	 * @param id
	 * @param adresse
	 * @param ort
	 * @param zip
	 */
	public Adresse(Long id, String adresse, String ort, String zip) {
		this.id = id;
		this.adresse = adresse;
		this.ort = ort;
		this.zip = zip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public void setAdresse(String adress) {
		this.adresse = adress;
	}
	
	public String getOrt() {
		return ort;
	}
	
	public void setOrt(String city) {
		this.ort = city;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adresse other = (Adresse) obj;
		return Objects.equals(adresse, other.adresse) && Objects.equals(id, other.id) && Objects.equals(ort, other.ort)
				&& Objects.equals(zip, other.zip);
	}
	
}
