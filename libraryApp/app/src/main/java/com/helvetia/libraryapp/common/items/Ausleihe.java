package com.helvetia.libraryapp.common.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


/**
 * DTO-Klasse für die DB-Entity Ausleihe <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #id}: Eindeutiges Attribut der Ausleihe</li>
 * <li>{@link #ausleihedatum}: Erstellungsdatum der Ausleihe. Wird automatisch eingefüllt auf der DB</li>
 * <li>{@link #ausleihedauer}: Dauer bis die Ausleihe abläuft </li>
 * <li>{@link #kunde}: Kunde, der die Ausleihe betrifft</li>
 * <li>{@link #medium}: Medium, das die Ausleihe betrifft</li>
 * <li>{@link #DATE_FORMAT}: Konstante, um Datum zu formatieren</li>
 * </ul>
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
@JsonInclude(Include.NON_NULL)
public class Ausleihe extends BaseItem {
    private Long id;
    private Date ausleihedatum;
    private Long ausleihedauer;
    private Kunde kunde;
    private Medium medium;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);

    /**
     * Standard constructor
     */
    public Ausleihe() {
        this.kunde = new Kunde();
        this.medium = new Medium();
        this.ausleihedatum = new Date();
        this.ausleihedauer = 14L;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getIdToDeleteItem() {
        return medium.getId();
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

    /**
     * Methode, die {@link #ausleihedatum} mit {@link #ausleihedauer} verrechnet und das Ergebnis als String zurückgibt
     * @return Formatiertes Datum als Datum
     */
    public String returnDateAsString() {
        if (ausleihedatum != null && ausleihedauer != null) {
            return DATE_FORMAT.format(new Date(ausleihedatum.getTime() + TimeUnit.DAYS.toMillis(ausleihedauer)));
        } else {
            return "";
        }
    }

    /**
     * Methode, um ein JSON-String zu lesen und in eine Liste von Ausleihen zu "verwandeln"(parsen)
     * @param strJsonObject String mit Ausleihe-Objekten im JSON-Format
     * @return Liste mit Ausleihen, welche im String enthalten waren
     */
    public static List<Ausleihe> parseJsonObjects(String strJsonObject) {
        List<Ausleihe> itemList = new ArrayList<>();
        if (strJsonObject != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                itemList = mapper.readValue(strJsonObject, new TypeReference<List<Ausleihe>>(){});
            } catch (Exception ignored) {
            }
        }
        return itemList;
    }

}
