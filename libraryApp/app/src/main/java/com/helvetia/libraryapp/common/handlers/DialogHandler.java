package com.helvetia.libraryapp.common.handlers;

import android.app.AlertDialog;
import android.content.Context;

import com.helvetia.libraryapp.common.items.BaseItem;
import com.helvetia.libraryapp.communication.requests.RestDeleteRequest;

/**
 * Klasse zum Erstellen von Dialogen
 *
 * @author Simon
 * @version 1.0.0
 */
public class DialogHandler {

    /**
     * Methode zum Erstellen eines Dialogs mit Message
     * @param context Context in dem der Dialog erscheinen soll
     * @param message Message des Dialogs
     */
    public static void openSimpleDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("OK", ((dialogInterface, i) -> {}));
        builder.create().show();
    }

    /**
     * Methode zum Erstellen eines Dialogs mit Titel und Message
     * @param context Context in dem der Dialog erscheinen soll
     * @param title Titel des Dialogs
     * @param message Message des Dialogs
     */
    public static void openSimpleDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {});
        builder.create().show();
    }

    /**
     * Methode zum Erstellen eines Bestätigungsdialog beim Löschen der Einträge
     * @param context Context in dem der Dialog erscheinen soll
     * @param title Titel des Dialogs
     * @param message Message des Dialogs
     * @param request Delete-Request, die nach dem Bestätigen ausgelöst werden sollte
     * @param <T> Type der Request
     */
    public static <T extends BaseItem> void openDeleteConfirmDialog(Context context, String title, String message, RestDeleteRequest<T> request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("Abbrechen", (dialogInterface, i) -> {});
        builder.setPositiveButton("Löschen", (dialogInterface, i) -> request.execute());
        builder.create().show();
    }
}
