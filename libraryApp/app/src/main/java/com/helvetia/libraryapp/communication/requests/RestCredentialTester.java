package com.helvetia.libraryapp.communication.requests;

import android.os.AsyncTask;

import com.helvetia.libraryapp.common.SingletonAppData;
import com.helvetia.libraryapp.communication.listeners.AsyncLoginListener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Klasse, um
 * auszuführen <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #listener}: Listener, welcher asynchron die HTTP-Response verarbeitet</li>
 * <li>{@link #responseCode}: Response-Code der HTTP-Anfrage</li>
 * </ul>
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public class RestCredentialTester extends AsyncTask<String, Integer, String> {
    private final AsyncLoginListener listener;

    private int responseCode;

    /**
     * Constructor
     * @param listener Referenz auf den Listener, welcher asynchron die HTTP-Response verarbeitet
     */
    public RestCredentialTester(AsyncLoginListener listener) {
        this.listener = listener;
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
        String response = "";
        // Create connection
        HttpURLConnection con = null;
        try {
            URL url = new URL(SingletonAppData.getInstance().getApiURL() + "/ausleihe");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", SingletonAppData.getInstance().getAuthStr());
            responseCode = con.getResponseCode();
        } catch (Exception e) {
            responseCode = -1;
            response = Objects.requireNonNull(e.getLocalizedMessage()).trim();
        } finally {
            if (con != null) con.disconnect();
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onProgressUpdate(Integer... progress) {}

    /**
     * Diese Methode wird automatisch aufgerufen, nachdem die HTTP-Response
     * eingetroffen ist. Wird dazu verwendet, das UI zu aktualisieren.
     * Achtung: Läuft im UI-Thread.
     * @param result eine Zeichenkette, z.B. die Fehlermeldung
     */
    @Override
    protected void onPostExecute(String result) {
        if (responseCode == HttpURLConnection.HTTP_OK) {
            listener.success();
        } else if (responseCode < 0) {
            listener.handleError(result);
        } else {
            listener.handleError(responseCode);
        }
    }

}
