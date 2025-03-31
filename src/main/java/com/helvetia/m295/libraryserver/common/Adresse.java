package com.helvetia.m295.libraryserver.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.helvetia.m295.libraryserver.model.AdresseRepository;
import com.helvetia.m295.libraryserver.service.AdresseController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

/**
 * Klasse für die DB-Entity Adresse
 * <strong>Attribute:</strong>
 * <ul>
 * <li>id: Eindeutiges Attribut der Adresse</li>
 * <li>adresse: Strassenname und Hausnummer der Adresse eines Kunden</li>
 * <li>ort: Ort der Adresse eines Kunden</li>
 * <li>zip: ZIP-Code der Adresse eines Kunden</li>
 * </ul>
 *
 * @author Simon Fäs
 * @version 1.0.0
 * @see AdresseRepository
 * @see AdresseController
 */
@Entity
@DynamicInsert
@DynamicUpdate
@JsonInclude(Include.NON_NULL)
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @Length(max = 100)
    @Pattern(regexp = "^[a-zäöü-]+ [0-9]+[a-z]?$", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(nullable = false, length = 100)
    private String adresse;
    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Pattern(regexp = "^[a-zäöü-]+$", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(nullable = false, length = 50)
    private String ort;
    @NotNull
    @NotEmpty
    @Length(max = 10)
    @Pattern(regexp = "[A-z0-9]")
    @Column(nullable = false, length = 10)
    private String zip;

    /**
     * Standard constructor
     */
    public Adresse() {
    }

    /**
     * Constructor mit allen Attributen als Parameter
     *
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
