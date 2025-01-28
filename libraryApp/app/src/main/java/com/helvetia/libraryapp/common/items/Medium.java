package com.helvetia.libraryapp.common.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


/**
 * DTO-Klasse für die DB-Entity Medium <br>
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
 * @version 1.0.0
 * @author Simon Fäs
 */

@JsonInclude(Include.NON_NULL)
public class Medium extends BaseItem {
    private Long id;
    private String titel;
    private String autor;
    private String genre;
    private Short altersfreigabe;
    private Long isbn;
    private String standortcode;

    /**
     * Standard constructor
     */
    public Medium() {}

    /**
     * Constructor mit allen Attributen als Parameter
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

    /**
     * Methode, um ein JSON-String zu lesen und in eine Liste von Medien zu "verwandeln"(parsen)
     * @param strJsonObject String mit Medien-Objekten im JSON-Format
     * @return Liste mit Medien, welche im String enthalten waren
     */
    public static List<Medium> parseJsonObjects(String strJsonObject) {
        List<Medium> itemList = new ArrayList<>();
        if (strJsonObject != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                itemList = mapper.readValue(strJsonObject, new TypeReference<List<Medium>>(){});
            } catch (Exception ignored) {
            }
        }
        return itemList;
    }

}
