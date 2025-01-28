package com.helvetia.libraryapp.communication.requests;

import android.os.AsyncTask;

import com.helvetia.libraryapp.common.SingletonAppData;
import com.helvetia.libraryapp.communication.listeners.AsyncTableListener;
import com.helvetia.libraryapp.common.items.BaseItem;
import com.helvetia.libraryapp.common.items.Ausleihe;
import com.helvetia.libraryapp.common.items.Medium;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Klasse, um HTTP-Anfragen der Methode "GET" für die Klasse {@link Medium} auszuführen <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #listener}: Listener, welcher asynchron die HTTP-Response verarbeitet</li>
 * <li>{@link #responseCode}: Response-Code der HTTP-Anfrage</li>
 * </ul>
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public class RestGetRequestForAusleihe extends AsyncTask<String, Integer, String> {
    private final AsyncTableListener<Ausleihe> listener;

    private int responseCode;

    /**
     * Constructor
     * @param listener Referenz auf den Listener, welcher asynchron die HTTP-Response verarbeitet
     */
    public RestGetRequestForAusleihe(AsyncTableListener<Ausleihe> listener) {
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
        StringBuilder response = new StringBuilder();
        String line;
        // Create connection
        HttpURLConnection con = null;
        BufferedReader br = null;
        try {
            URL url = new URL(SingletonAppData.getInstance().getApiURL() + "/ausleihe");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", SingletonAppData.getInstance().getAuthStr());

            responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
            }
        } catch (Exception e) {
            responseCode = -1;
            response = new StringBuilder(Objects.requireNonNull(e.getLocalizedMessage()).trim());
        } finally {
            if (br != null) try { br.close(); } catch (Exception ignored) {}
            if (con != null) con.disconnect();
        }
        return response.toString();
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
            List<Ausleihe> medien = new ArrayList<>();
            if (result.endsWith("]")) {
                medien.addAll(Ausleihe.parseJsonObjects(result));
            } else {
                medien.add(BaseItem.parseJsonObject(result, Ausleihe.class));
            }
            listener.updateTable(medien);
        } else if (responseCode < 0) {
            listener.handleError(result);
        } else {
            listener.handleError(responseCode);
        }
    }

}
