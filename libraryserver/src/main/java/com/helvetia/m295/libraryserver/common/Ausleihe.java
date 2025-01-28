package com.helvetia.m295.libraryserver.common;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.helvetia.m295.libraryserver.model.AusleiheRepository;
import com.helvetia.m295.libraryserver.service.AusleiheController;

import jakarta.persistence.*;

/**
 * Klasse für die DB-Entity Ausleihe
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #id}: Eindeutiges Attribut der Ausleihe</li>
 * <li>{@link #ausleihedatum}: Erstellungsdatum der Ausleihe. Wird automatisch eingefüllt auf der DB</li>
 * <li>{@link #ausleihedauer}: Dauer bis die Ausleihe abläuft </li>
 * <li>{@link #kunde}: Kunde, der die Ausleihe betrifft</li>
 * <li>{@link #medium}: Medium, das die Ausleihe betrifft</li>
 * </ul>
 * 
 * @version 1.0.0
 * @author Simon Fäs
 * @see AusleiheRepository
 * @see AusleiheController
 */
@Entity
@DynamicInsert
@DynamicUpdate
@JsonInclude(Include.NON_NULL)
public class Ausleihe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date ausleihedatum;
	private Long ausleihedauer;
	@ManyToOne
	@JoinColumn(name = "kundenid", nullable = false)
	private Kunde kunde;
	@ManyToOne
	@JoinColumn(name = "medienid", nullable = false)
	private Medium medium;
	
	/**
	 * Standard constructor
	 */
	public Ausleihe() {
		this.kunde = new Kunde();
		this.medium = new Medium();
	}
	
	/**
	 * Constructor mit id, kunde und medium als Parameter
	 * @param id
	 * @param kunde
	 * @param medium
	 */
	public Ausleihe(Long id, Kunde kunde, Medium medium) {
		this.id = id;
		this.ausleihedatum = new Date();
		this.ausleihedauer = 14L;
		this.kunde = kunde;
		this.medium = medium;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAusleihedatum() {
		return ausleihedatum;
	}

	public void setAusleihedatum(Date ausleiheDatum) {
		this.ausleihedatum = ausleiheDatum;
	}

	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public Medium getMedium() {
		return medium;
	}

	public void setMedium(Medium medium) {
		this.medium = medium;
	}

	public Long getAusleihedauer() {
		return ausleihedauer;
	}

	public void setAusleihedauer(Long ausleiheDauer) {
		this.ausleihedauer = ausleiheDauer;
	}

}
