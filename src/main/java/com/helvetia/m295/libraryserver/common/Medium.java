package com.helvetia.m295.libraryserver.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.helvetia.m295.libraryserver.model.MediumRepository;
import com.helvetia.m295.libraryserver.service.MediumController;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

/**
 * Klasse für die DB-Entity Medium
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #id}: Eindeutiges Attribut des Mediums</li>
 * <li>{@link #titel}: Titel des Mediums</li>
 * <li>{@link #autor}: Autor des Mediums</li>
 * <li>{@link #genre}: Genre des Mediums</li>
 * <li>{@link #altersfreigabe}: Altersfreigabe des Mediums</li>
 * <li>{@link #isbn}: ISBN des Mediums</li>
 * <li>{@link #standortcode}: Standort des Mediums(In welchem Regal es eingeordnet ist)</li>
 * </ul>
 *
 * @author Simon
 * @version 1.0.0
 * @see MediumRepository
 * @see MediumController
 */
@Entity
@DynamicInsert
@DynamicUpdate
@JsonInclude(Include.NON_NULL)
public class Medium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Length(max = 50)
    @Column(nullable = false, length = 50)
    private String titel;
    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Pattern(regexp = "^[A-zäöü-]+ [A-zäöü-]+$", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(nullable = false, length = 50)
    private String autor;
    @NotNull
    @NotEmpty
    @Length(max = 20)
    @Pattern(regexp = "^[A-z]+$")
    @Column(nullable = false, length = 20)
    private String genre;
    @NotNull
    @Min(0)
    @Max(127)
    @Column(nullable = false)
    private Short altersfreigabe;
    @NotNull
    @Min(0)
    @Max(9_999_999_999_999L)
    @Column(nullable = false)
    private Long isbn;
    @NotNull
    @NotEmpty
    @Length(max = 20)
    @Pattern(regexp = "^\\w+$")
    @Column(nullable = false, length = 20)
    private String standortcode;

    /**
     * Standard constructor
     */
    public Medium() {
    }

    /**
     * Constructor mit allen Attributen als Parameter
     *
     * @param id
     * @param titel
     * @param autor
     * @param genre
     * @param altersfreigabe
     * @param isbn
     * @param standortcode
     */
    public Medium(Long id, String titel, String autor, String genre, Short altersfreigabe, Long isbn,
                  String standortcode) {
        this.id = id;
        this.titel = titel;
        this.autor = autor;
        this.genre = genre;
        this.altersfreigabe = altersfreigabe;
        this.isbn = isbn;
        this.standortcode = standortcode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Short getAltersfreigabe() {
        return altersfreigabe;
    }

    public void setAltersfreigabe(Short altersfreigabe) {
        this.altersfreigabe = altersfreigabe;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getStandortcode() {
        return standortcode;
    }

    public void setStandortcode(String standortCode) {
        this.standortcode = standortCode;
    }

}
