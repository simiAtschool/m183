package com.helvetia.libraryapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.helvetia.libraryapp.R;
import com.helvetia.libraryapp.ui.ausleiheActivity.AusleiheEditActivity;
import com.helvetia.libraryapp.ui.ausleiheActivity.AusleiheTableActivity;
import com.helvetia.libraryapp.ui.mediumActivity.MedienEditActivity;
import com.helvetia.libraryapp.ui.mediumActivity.MedienTableActivity;

/**
 * Abstrakte Klasse, die von allen Activity-Klassen geerbt wird.
 * Implementiert bereits die Methoden, um das Menü zu erstellen.
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Methode, um Menü zu erstellen.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Methode zur Bestimmung, wohin navigiert wird.
     * @param item Ausgewählter Menü-Punkt
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.menuMediaTable:
                startActivity(new Intent(this, MedienTableActivity.class));
                return true;
            case R.id.menuMediaEdit:
                startActivity(new Intent(this, MedienEditActivity.class));
                return true;
            case R.id.menuBorrowTable:
                startActivity(new Intent(this, AusleiheTableActivity.class));
                return true;
            case R.id.menuBorrowEdit:
                startActivity(new Intent(this, AusleiheEditActivity.class));
                return true;
            case R.id.menuLogout:
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
