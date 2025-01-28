package com.helvetia.libraryapp.common.handlers;

import android.content.Context;

/**
 * Klasse zum Entschlüsseln von HTTP-Response-Codes
 *
 * @author Simon
 * @version 1.0.0
 */
public class ResponseHandler {

    /**
     * Methode zum Entschlüsseln von HTTP-Response-Codes
     * @param context Context, in dem Dialog dargestellt werden sollte
     * @param code Response-Code
     */
    public static void errorHandler(Context context, int code) {
        switch (code) {
            case 401:
                DialogHandler.openSimpleDialog(context, "Logindaten falsch (Code 401)", "Ihre Logindaten waren falsch");
                break;
            case 403:
                DialogHandler.openSimpleDialog(context, "Keine Berechtigung (Code 403)",
                        "Sie haben keine Berechtigungen, um diese Funktion zu nutzen");
                break;
            case 404:
                DialogHandler.openSimpleDialog(context, "Nichts gefunden (Code 404)",
                        "Eintrag zur Verarbeitung wurde nicht gefunden \r\n" +
                                "Bitte überprüfen Sie, ob der Eintrag vorhanden ist, den Sie suchen oder mit dem Sie ein Eintrag erstellen möchten");
                break;
            case 409:
                DialogHandler.openSimpleDialog(context, "Konflikt (Code: 409)",
                        "Beim Hinzufügen sind wurde ein Konflikt entdeckt. \r\n" +
                        "Bitte überprüfen Sie, ob bestimmte Werte nicht schon in anderen Einträgen vorhanden sind");
                break;
            case 500:
                DialogHandler.openSimpleDialog(context, "Server-Error (Code 500)", "Server hatte Probleme beim Verarbeiten der Anfrage.");
                break;
            default:
                DialogHandler.openSimpleDialog(context, "Fehler (Code " + code + ")", "");
        }

    }

}
