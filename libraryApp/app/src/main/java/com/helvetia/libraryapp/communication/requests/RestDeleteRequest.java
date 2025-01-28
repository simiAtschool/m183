package com.helvetia.libraryapp.communication.requests;

import android.os.AsyncTask;

import com.helvetia.libraryapp.common.SingletonAppData;
import com.helvetia.libraryapp.communication.listeners.AsyncFormListener;
import com.helvetia.libraryapp.common.items.BaseItem;
import com.helvetia.libraryapp.common.items.Medium;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Klasse, um HTTP-Anfragen der Methode "DELETE" auszuführen <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #listener}: Listener, welcher asynchron die HTTP-Response verarbeitet</li>
 * <li>{@link #object}: Geändertes Objekt</li>
 * <li>{@link #responseCode}: Response-Code der HTTP-Anfrage</li>
 * </ul>
 *
 * @param <T> Typ des Objekts, welches bearbeitet werden sollte
 * @version 1.0.0
 * @author Simon Fäs
 */
public class RestDeleteRequest<T extends BaseItem> extends AsyncTask<String, Integer, String> {

    private final AsyncFormListener<T> listener;
    private final T object;

    private int responseCode;

    /**
     * Constructor
     *
     * @param listener Referenz auf den Listener, welcher asynchron die HTTP-Response
     *                 verarbeitet
     */
    public RestDeleteRequest(AsyncFormListener<T> listener, T object) {
        this.listener = listener;
        this.object = object;
    }

    /**
     * Diese Methode wird automatisch aufgerufen, nachdem der neue Thread mittels
     * <br><code>
     * task.execute();
     * </code><br>gestartet wurde
     * @param params ein Array von Zeichenketten, welche als Argumente beim Start
     *         des Threads übergeben wurden
     * @return eine Zeichenkette, z.B. die Fehlermeldung
     */
    @Override
    protected String doInBackground(String... params) {
        StringBuilder response = new StringBuilder();
        // Create connection
        HttpURLConnection con = null;
        try {
            URL url = new URL(SingletonAppData.getInstance().getApiURL() + "/" + (object instanceof Medium ? "medium/" : "ausleihe/") + object.getIdToDeleteItem().toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", SingletonAppData.getInstance().getAuthStr());

            responseCode = con.getResponseCode();
        } catch (Exception e) {
            response = new StringBuilder(Objects.requireNonNull(e.getLocalizedMessage()).trim());
        } finally {
            if (con != null) con.disconnect();
        }
        return response.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    /**
     * Diese Methode wird automatisch aufgerufen, nachdem die HTTP-Response
     * eingetroffen ist. Wird dazu verwendet, das UI zu aktualisieren.
     * Achtung: Läuft im UI-Thread.
     * @param result eine Zeichenkette, z.B. die Fehlermeldung
     */
    @Override
    protected void onPostExecute(String result) {
        if (responseCode == HttpURLConnection.HTTP_OK) {
            listener.goBack();
        } else if (result.length() > 0) {
            listener.handleError(result);
        } else {
            listener.handleError(responseCode);
        }
    }

}
