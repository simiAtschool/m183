package com.helvetia.libraryapp.communication.listeners;

/**
 * Callback Interface, um Fehler asynchron zu behandeln.
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public interface AsyncBaseListener {

    /**
     * Gibt eine Fehlermeldung aus
     * Achtung: Fehler nicht mit einem Toast ausgeben
     * @param result die auszugebende Fehlermeldung
     */
    void handleError(String result);

    /**
     * Gibt eine Fehlermeldung aus
     * Achtung: Fehler nicht mit einem Toast ausgeben
     * @param code der auszugebende Fehlercode
     */
    void handleError(int code);

}
