package com.helvetia.libraryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.helvetia.libraryapp.R;
import com.helvetia.libraryapp.common.SingletonAppData;
import com.helvetia.libraryapp.ui.ausleiheActivity.AusleiheTableActivity;
import com.helvetia.libraryapp.common.handlers.DialogHandler;
import com.helvetia.libraryapp.common.handlers.ResponseHandler;
import com.helvetia.libraryapp.communication.listeners.AsyncLoginListener;
import com.helvetia.libraryapp.communication.requests.RestCredentialTester;

/**
 * Klasse zur Darstellung des Anmeldefensters.
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #inputName}: View des Inputs für den Name </li>
 * <li>{@link #inputPassword}: View des Inputs für das Passwort </li>
 * </ul>
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public class LoginActivity extends AppCompatActivity implements AsyncLoginListener {

    private EditText inputName;
    private EditText inputPassword;

    /**
     * Methode, die beim Erstellen der View ausgelöst wird.
     * @param savedInstanceState Gespeicherter Zustand der View
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //        Inputs
        inputName = findViewById(R.id.inputName);
        inputPassword = findViewById(R.id.inputPassword);

        findViewById(R.id.btnLogin).setOnClickListener(view -> saveAction());

    }

    /**
     * Methode, um Credentials(Anmeldedaten) zu verifizieren
     */
    private void saveAction() {
        SingletonAppData.getInstance().setAuthStr(inputName.getText().toString(), inputPassword.getText().toString());
        new RestCredentialTester(this).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override

    public void success() {
        startActivity(new Intent(this, AusleiheTableActivity.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleError(String result) {
        inputPassword.setText("");
        DialogHandler.openSimpleDialog(this, result);
        Log.w(getClass().getName(), result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleError(int code) {
        inputPassword.setText("");
        ResponseHandler.errorHandler(this, code);
        Log.w(getClass().getName(), String.valueOf(code));
    }
}
