package com.helvetia.libraryapp.communication.listeners;

//TODO: Beschreibung verbessern
/**
 * Callback Interface, um asynchron zu bestätigen, dass Login erfolgreich war.
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public interface AsyncLoginListener extends AsyncBaseListener {

    /**
     * Methode, welche nach erfolgreichem Überprüfen der Logindaten aufgerufen werden kann.
     */
    void success();

}
