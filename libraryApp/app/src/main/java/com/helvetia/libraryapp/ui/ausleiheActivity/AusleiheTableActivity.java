package com.helvetia.libraryapp.ui.ausleiheActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.helvetia.libraryapp.common.handlers.DialogHandler;
import com.helvetia.libraryapp.common.handlers.ResponseHandler;
import com.helvetia.libraryapp.communication.listeners.AsyncTableListener;
import com.helvetia.libraryapp.R;
import com.helvetia.libraryapp.common.items.Ausleihe;
import com.helvetia.libraryapp.communication.requests.RestGetRequestForAusleihe;
import com.helvetia.libraryapp.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Klasse zur Darstellung der Datei "activity_ausleihe_table.xml". <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #HEADERS}: Konstanter Array der Kopfzeile der Tabelle</li>
 * <li>{@link #tableView}: View der Tabelle</li>
 * </ul>
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public class AusleiheTableActivity extends BaseActivity implements AsyncTableListener<Ausleihe> {

    private TableView<Ausleihe> tableView;

    private static final String[] HEADERS = { "Medien-ID", "Kunden-ID", "Rückgabedatum" };

    /**
     * Die Methode wird beim Öffnen der View ausgeführt
     * @param savedInstanceState Ressourcenpaket mit Daten gespeicherten Daten
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ausleihe_table);

        tableView = findViewById(R.id.tableAusleihe);
        tableView.setColumnCount(HEADERS.length);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(HEADERS.length);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 3);
        tableView.setColumnModel(columnModel);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, HEADERS));
        tableView.setDataAdapter(new AusleiheTableDataAdapter(this, new ArrayList<>()));
        tableView.addDataLongClickListener((rowIndex, clickedData) -> {
            Intent i = new Intent(this, AusleiheEditActivity.class);
            i.putExtra("ausleihe", clickedData);
            startActivity(i);
            return true;
        });
        new RestGetRequestForAusleihe(this).execute();

        findViewById(R.id.addAusleihe).setOnClickListener(v -> startActivity(new Intent(this, AusleiheEditActivity.class)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context getContext() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTable(List<Ausleihe> data) {
        tableView.getDataAdapter().clear();
        tableView.getDataAdapter().addAll(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleError(String result) {
        DialogHandler.openSimpleDialog(this, result);
        Log.w(getClass().getName(), result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleError(int code) {
        ResponseHandler.errorHandler(this, code);
        Log.w(getClass().getName(), String.valueOf(code));
    }
}
