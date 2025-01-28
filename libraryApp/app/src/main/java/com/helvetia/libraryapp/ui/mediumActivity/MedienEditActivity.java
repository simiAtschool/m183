package com.helvetia.libraryapp.ui.mediumActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.helvetia.libraryapp.communication.listeners.AsyncFormListener;
import com.helvetia.libraryapp.common.handlers.DialogHandler;
import com.helvetia.libraryapp.R;
import com.helvetia.libraryapp.common.handlers.ResponseHandler;
import com.helvetia.libraryapp.common.items.Medium;
import com.helvetia.libraryapp.communication.requests.RestDeleteRequest;
import com.helvetia.libraryapp.communication.requests.RestPostRequest;
import com.helvetia.libraryapp.communication.requests.RestPutRequest;
import com.helvetia.libraryapp.ui.BaseActivity;

/**
 * Klasse zur Darstellung der Datei "activity_medien_edit.xml". <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #isCreate}: Konstanter Array der Kopfzeile der Tabelle</li>
 * <li>{@link #medium}: View der Tabelle</li>
 * <li>{@link #inputTitle}: View des Inputs für den Titel</li>
 * <li>{@link #inputAuthor}: View des Inputs für den Autor</li>
 * <li>{@link #inputISBN}: View des Inputs für die ISBN-Nummer</li>
 * <li>{@link #inputGenre}: View des Inputs für das Genre</li>
 * <li>{@link #inputRating}: View des Inputs für die Altersfreigabe</li>
 * <li>{@link #inputPosition}: View des Inputs für die Position des Buches</li>
 * </ul>
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public class MedienEditActivity extends BaseActivity implements AsyncFormListener<Medium> {

    private boolean isCreate;
    private Medium medium;

    private EditText inputTitle;
    private EditText inputAuthor;
    private EditText inputISBN;
    private EditText inputGenre;
    private EditText inputRating;
    private EditText inputPosition;

    /**
     * Methode, die beim Erstellen der View ausgelöst wird.
     * @param savedInstanceState Gespeicherter Zustand der View
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medien_edit);
//        Inputs
        inputTitle = findViewById(R.id.inputTitle);
        inputAuthor = findViewById(R.id.inputAuthor);
        inputISBN = findViewById(R.id.inputISBN);
        inputGenre = findViewById(R.id.inputGenre);
        inputRating = findViewById(R.id.inputRating);
        inputPosition = findViewById(R.id.inputPosition);

//        Get ressources from intent
        Bundle extras = getIntent().getExtras();
        Object value = null;
        if (extras != null) {
            value = extras.get("medium");
        }

//        Init buttons
        findViewById(R.id.saveMedium).setOnClickListener(view -> saveAction());
        Button deleteButton = findViewById(R.id.deleteMedium);
        findViewById(R.id.cancelMedium).setOnClickListener(view -> goBack());

//        Determine if new object is created or not
        if (value instanceof Medium) {
            isCreate = false;
            medium = (Medium) value;
            resetForm();
            deleteButton.setOnClickListener(view ->
                DialogHandler.openDeleteConfirmDialog(this, "Löschen?",
                        "Ihr Eintrag wird dadurch für immer gelöscht",
                        new RestDeleteRequest<>(this, medium))
            );
        } else {
            isCreate = true;
            deleteButton.setVisibility(View.GONE);
        }
    }

    /**
     * Methode zum Erstellen und Speichern von Einträgen
     */
    private void saveAction() {
        if (inputTitle.getText().toString().isEmpty() || inputAuthor.getText().toString().isEmpty()) {
            handleError("Sie haben vergessen, Titel oder Autor einzugeben");
            return;
        }

        Short altersfreigabe = null;
        Long isbn = null;
        try {
            altersfreigabe = Short.valueOf(inputRating.getText().toString());
        } catch (Exception e) {
        }
        try {
            isbn = Long.valueOf(inputISBN.getText().toString());
        } catch (Exception e) {
        }

        Medium medium = new Medium();
        medium.setId(this.medium != null ? this.medium.getId() : null);
        medium.setTitel(inputTitle.getText().toString());
        medium.setAutor(inputAuthor.getText().toString());
        medium.setGenre(inputGenre.getText().toString());
        medium.setAltersfreigabe(altersfreigabe);
        medium.setIsbn(isbn);
        medium.setStandortcode(inputPosition.getText().toString());

        if (isCreate) {
            new RestPostRequest<>(this, medium).execute();
        } else {
            new RestPutRequest<>(this, medium).execute();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void updateForm(Medium medium) {
        if (medium != null) {
            setInputs(medium);
        } else {
            resetForm();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void resetForm() {
        setInputs(this.medium);
    }

    /**
     * Methode, um den Inhalt der Inputs zu setzen.
     * @param medium Medium zum Setzen der Inhalte
     */
    private void setInputs(Medium medium) {
        if (medium != null) {
            inputTitle.setText(medium.getTitel() != null ? medium.getTitel() : "");
            inputAuthor.setText(medium.getAutor() != null ? medium.getAutor() : "");
            inputGenre.setText(medium.getGenre() != null ? medium.getGenre() : "");
            inputPosition.setText(medium.getStandortcode() != null ? medium.getStandortcode() : "");
            inputRating.setText(medium.getAltersfreigabe() != null ? medium.getAltersfreigabe().toString() : "");
            inputISBN.setText(medium.getIsbn() != null ? medium.getIsbn().toString() : "");
        } else {
            inputTitle.setText("");
            inputAuthor.setText("");
            inputGenre.setText("");
            inputPosition.setText("");
            inputRating.setText("");
            inputISBN.setText("");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goBack() {
        startActivity(new Intent(this, MedienTableActivity.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleError(String result) {
        DialogHandler.openSimpleDialog(this, result);
        Log.w(getClass().getName(), result);
        resetForm();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleError(int code) {
        Log.w(getClass().getName(), String.valueOf(code));
        ResponseHandler.errorHandler(this, code);
        resetForm();
    }
}
