package com.helvetia.libraryapp.communication.listeners;

/**
 * Callback Interface, wird verwendet, um asynchron erhaltene Daten im Formular zu aktualisieren.
 * @param <T> Datentyp des DTOs mit den Daten des Formulars.
 * @author Simon Fäs
 */
public interface AsyncFormListener<T> extends AsyncBaseListener {

    /**
     * Aktualisiert alle Felder der Aktivität anhand der übergebenen Daten.
     * @param object DTO mit den übergabedaten
     */
    void updateForm(T object);

    /**
     * Löscht alle Felder der Aktivität.
     */
    void resetForm();

    /**
     * Wechselt zu einer anderen Aktivität.
     */
    void goBack();

}
