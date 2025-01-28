package com.helvetia.libraryapp.common;

import android.util.Base64;

/**
 * Klasse, um Daten über die ganze Applikation hinweg zur Verfügung zu stellen.
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #data}: Feld mit Referenz auf sich selbst</li>
 * <li>{@link #authStr}: String zur Authorization</li>
 * <li>{@link #apiURL}: String mit der URL der API</li>
 * </ul>
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public class SingletonAppData {

    private static SingletonAppData data;

    private String authStr;

    private final String apiURL = "http://192.168.1.190:8080/bibliothek";

    private SingletonAppData() {}

    public static SingletonAppData getInstance() {
        if (data == null) {
            data = new SingletonAppData();
        }
        return data;
    }

    public String getAuthStr() {
        return authStr;
    }

    public void setAuthStr(String name, String password) {
        this.authStr = "Basic " + Base64.encodeToString((name + ":" + password).getBytes(), Base64.NO_WRAP);
    }

    public String getApiURL() {
        return apiURL;
    }

}
