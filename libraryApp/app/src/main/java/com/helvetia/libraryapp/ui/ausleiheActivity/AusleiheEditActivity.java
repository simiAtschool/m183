package com.helvetia.libraryapp.ui.ausleiheActivity;

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
import com.helvetia.libraryapp.common.items.Ausleihe;
import com.helvetia.libraryapp.communication.requests.RestDeleteRequest;
import com.helvetia.libraryapp.communication.requests.RestPostRequest;
import com.helvetia.libraryapp.communication.requests.RestPutRequest;
import com.helvetia.libraryapp.ui.BaseActivity;

/**
 * Klasse zur Darstellung der Datei "activity_ausleihe_edit.xml". <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #isCreate}: Konstanter Array der Kopfzeile der Tabelle</li>
 * <li>{@link #ausleihe}: View der Tabelle</li>
 * <li>{@link #inputMedium}: View des Inputs für die Medium-Id</li>
 * <li>{@link #inputKunde}: View des Inputs für die Kunden-Id</li>
 * </ul>
 *
 * @author Simon Fäs
 * @version 1.0.0
 */
public class AusleiheEditActivity extends BaseActivity implements AsyncFormListener<Ausleihe> {

    private boolean isCreate;
    private Ausleihe ausleihe;

    private EditText inputMedium;
    private EditText inputKunde;

    /**
     * Die Methode wird beim Öffnen der View ausgeführt
     * @param savedInstanceState Ressourcenpaket mit Daten gespeicherten Daten
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ausleihe_edit);
//        Inputs
        inputMedium = findViewById(R.id.inputMedium);
        inputKunde = findViewById(R.id.inputKunde);

        Bundle extras = getIntent().getExtras();
        Object value = null;
        if (extras != null) {
            value = extras.get("ausleihe");
        }
        Button saveButton = findViewById(R.id.saveAusleihe);
        saveButton.setOnClickListener(view -> saveAction());
        findViewById(R.id.cancelAusleihe).setOnClickListener(view -> goBack());

        if (value instanceof Ausleihe) {
            ausleihe = (Ausleihe) value;
            isCreate = false;
            resetForm();
            saveButton.setText("Verlängern");
            findViewById(R.id.deleteAusleihe).setOnClickListener(view ->
                    DialogHandler.openDeleteConfirmDialog(
                            this, "Löschen?",
                            "Ihr Eintrag wird dadurch für immer gelöscht",
                            new RestDeleteRequest<>(this, ausleihe))
            );
            inputMedium.setEnabled(false);
            inputKunde.setEnabled(false);
        } else {
            isCreate = true;
            findViewById(R.id.deleteAusleihe).setVisibility(View.GONE);
            findViewById(R.id.beschreibungAusleihdauer).setVisibility(View.GONE);
        }
    }

    /**
     * Methode zum Erstellen und Speichern von Einträgen
     */
    private void saveAction() {
        if (inputMedium.getText().toString().isEmpty() || inputKunde.getText().toString().isEmpty()) {
            handleError("Sie haben vergessen, Medien-Id oder Kunden-Id einzugeben");
            return;
        }
        Ausleihe ausleihe = new Ausleihe();

        ausleihe.setId(this.ausleihe != null ? this.ausleihe.getId() : null);
        try {
            ausleihe.getMedium().setId(Long.valueOf(inputMedium.getText().toString()));
            ausleihe.getKunde().setId(Long.valueOf(inputKunde.getText().toString()));
        } catch (Exception e) {
        }
        ausleihe.setAusleihedauer(this.ausleihe.getAusleihedauer() != null ? this.ausleihe.getAusleihedauer() + 14 : 14);

        if (isCreate) {
            new RestPostRequest<>(this, ausleihe).execute();
        } else {
            new RestPutRequest<>(this, ausleihe).execute();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void updateForm(Ausleihe ausleihe) {
        if (ausleihe != null) {
            setInputs(ausleihe);
        } else {
            resetForm();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void resetForm() {
        setInputs(this.ausleihe);
    }

    /**
     * Methode, um den Inhalt der Inputs zu setzen.
     *
     * @param ausleihe Ausleihe zum Setzen der Inhalte
     */
    private void setInputs(Ausleihe ausleihe) {
        try {
            inputMedium.setText(ausleihe.getMedium().getId() != null ? ausleihe.getMedium().getId().toString() : "");
            inputMedium.invalidate();
            inputKunde.setText(ausleihe.getKunde().getId() != null ? ausleihe.getKunde().getId().toString() : "");
            inputKunde.invalidate();
        } catch (NullPointerException e) {
            inputMedium.setText("");
            inputKunde.setText("");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goBack() {
        startActivity(new Intent(this, AusleiheTableActivity.class));
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
        ResponseHandler.errorHandler(this, code);
        Log.w(getClass().getName(), String.valueOf(code));
        resetForm();
    }
}
