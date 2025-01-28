package com.helvetia.m295.libraryserver.common;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.helvetia.m295.libraryserver.model.KundeRepository;
import com.helvetia.m295.libraryserver.service.KundeController;

import jakarta.persistence.*;

/**
 * Klasse für die DB-Entity Kunde
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #id}: Eindeutiges Attribut des Kunden</li>
 * <li>{@link #vorname}: Vorname des Kunden</li>
 * <li>{@link #nachname}: Nachname des Kunden</li>
 * <li>{@link #geburtstag}: Geburtstag des Kunden</li>
 * <li>{@link #adresse}: Adresse des Kunden</li>
 * <li>{@link #email}: Email des Kunden</li>
 * </ul>
 * 
 * @version 1.0.0
 * @author Simon Fäs
 * @see KundeRepository
 * @see KundeController
 */
@Entity
@DynamicInsert
@DynamicUpdate
@JsonInclude(Include.NON_NULL)
public class Kunde {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String vorname;
	private String nachname;
	private Date geburtstag;
	@ManyToOne
	@JoinColumn(name = "addressid", nullable = false)
	private Adresse adresse;
	private String email;
	
	/**
	 * Standard constructor
	 */
	public Kunde() {
		this.adresse = new Adresse();
	}
	
	/**
	 * Constructor mit allen Attributen als Parameter
	 * @param id
	 * @param vorname
	 * @param nachname
	 * @param geburtstag
	 * @param adresse
	 * @param email
	 */
	public Kunde(Long id, String vorname, String nachname, Date geburtstag, Adresse adresse, String email) {
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtstag = geburtstag;
		this.adresse = adresse;
		this.email = email;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getVorname() {
		return vorname;
	}
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getNachname() {
		return nachname;
	}
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public Date getGeburtstag() {
		return geburtstag;
	}
	
	public void setGeburtstag(Date geburtstag) {
		this.geburtstag = geburtstag;
	}
	
	public Adresse getAdresse() {
		return adresse;
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

}
