package com.helvetia.libraryapp.ui.mediumActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.helvetia.libraryapp.common.handlers.DialogHandler;
import com.helvetia.libraryapp.common.handlers.ResponseHandler;
import com.helvetia.libraryapp.communication.listeners.AsyncTableListener;
import com.helvetia.libraryapp.R;
import com.helvetia.libraryapp.common.items.Medium;
import com.helvetia.libraryapp.communication.requests.RestGetRequestForMedium;
import com.helvetia.libraryapp.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Klasse zur Darstellung der Datei "activity_medien_table.xml". <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #HEADERS}: Konstanter Array der Kopfzeile der Tabelle</li>
 * <li>{@link #tableView}: View der Tabelle</li>
 * </ul>
 *
 * @author Simon Fäs
 * @version 1.0.0
 */
public class MedienTableActivity extends BaseActivity implements AsyncTableListener<Medium> {

    private static final String[] HEADERS = {"Id", "Titel", "Autor"};
    private SortableTableView<Medium> tableView;

    /**
     * Die Methode wird beim Öffnen der View ausgeführt
     * @param savedInstanceState Ressourcenpaket mit Daten gespeicherten Daten
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medien_table);
        tableView = findViewById(R.id.tableMedien);
        tableView.setColumnCount(HEADERS.length);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(HEADERS.length);
        columnModel.setColumnWeight(0, 1);
        columnModel.setColumnWeight(1, 4);
        columnModel.setColumnWeight(2, 4);
        tableView.setColumnModel(columnModel);
        tableView.setColumnComparator(0, (m1, m2) -> m1.getId().compareTo(m2.getId()));
        tableView.setColumnComparator(1, (m1, m2) -> m1.getTitel().compareTo(m2.getTitel()));
        tableView.setColumnComparator(2, (m1, m2) -> m1.getAutor().compareTo(m2.getAutor()));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, HEADERS));
        tableView.setDataAdapter(new MediumTableDataAdapter(this, new ArrayList<>()));
        tableView.addDataLongClickListener((rowIndex, clickedData) -> {
            Intent i = new Intent(this, MedienEditActivity.class);
            i.putExtra("medium", clickedData);
            startActivity(i);
            return true;
        });

        try {
            AsyncTask<String, Integer, String> async = new RestGetRequestForMedium(this);
            async.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.addAusleihe).setOnClickListener(view -> startActivity(new Intent(this, MedienEditActivity.class)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context getContext() {
        return tableView.getContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTable(List<Medium> data) {
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