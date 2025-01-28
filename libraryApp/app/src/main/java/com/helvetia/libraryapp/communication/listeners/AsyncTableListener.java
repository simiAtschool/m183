package com.helvetia.libraryapp.communication.listeners;

import android.content.Context;

import java.util.List;

/**
 * Callback Interface, wird verwendet, um asynchron erhaltene Daten in eine TableView zu schreiben.
 * @param <T> Datentyp des DTOs mit den Informationen eines Datensatzes (= Tabellenzeile).
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public interface AsyncTableListener<T> extends AsyncBaseListener {

    /**
     * Diese Methode gibt den Context der Tabelle zurück
     * @return Context der Tabelle
     */
    Context getContext();

    /**
     * Diese Methode füllt die Daten in die TableView.
     * @param data Daten, die in die Tabelle eingefüllt werden müssen.
     */
    void updateTable(final List<T> data);

}
