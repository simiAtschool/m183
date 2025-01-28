package com.helvetia.libraryapp.common.items;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * Abstrakte Basisklasse für alle DTOs
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public abstract class BaseItem implements Serializable {

    /**
     * Konvertiert ein JSON Objekt in ein Exemplar von dieser Klasse.
     *
     * @param strJsonObject die zu konvertierende Zeichenkette
     * @param type          Typ des zu erzeugenden Objekts
     * @return ein Objekt von dieser Klasse
     */
    public static <T extends BaseItem> T parseJsonObject(String strJsonObject, Class<T> type) {
        T obj = null;
        if (strJsonObject != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                obj = mapper.readValue(strJsonObject, type);
            } catch (Exception ignored) {
            }
        }
        return obj;
    }

    /**
     * Gibt die eindeutige Id vom Objekt zurück
     *
     * @return die eindeutige Id
     */
    public abstract Long getId();

    /**
     * Gibt den Wert vom Objekt zurück, um es zu löschen
     *
     * @return Wert, um Objekt zu löschen
     */
    public Long getIdToDeleteItem() {
        return getId();
    };

    /**
     * Konvertiert dieses Objekt in ein JSON Objekt
     *
     * @return eine Zeichenkette mit der JSON Repräsentation
     */
    public final String toJsonString() {
        String strJsonObj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            strJsonObj = mapper.writeValueAsString(this);
        } catch (Exception ignored) {
        }
        return strJsonObj;
    }
}
