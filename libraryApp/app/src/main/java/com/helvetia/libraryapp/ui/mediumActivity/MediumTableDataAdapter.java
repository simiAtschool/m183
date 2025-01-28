package com.helvetia.libraryapp.ui.mediumActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helvetia.libraryapp.common.items.Medium;
import com.helvetia.libraryapp.ui.mediumActivity.MedienTableActivity;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Klasse zur Darstellung der Daten in der Tabelle der Klasse {@link MedienTableActivity} <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #paddingLeft}: Konstante mit Wert für linkes Padding</li>
 * <li>{@link #paddingTop}: Konstante mit Wert für oberes Padding</li>
 * <li>{@link #paddingRight}: Konstante mit Wert für rechtes Padding</li>
 * <li>{@link #paddingBottom}: Konstante mit Wert für unteres Padding</li>
 * <li>{@link #textSize}: Konstante mit Wert für Textgrösse</li>
 * <li>{@link #typeface}: Konstante mit Wert für den Font</li>
 * <li>{@link #textColor}: Konstante mit Wert für Textfarbe</li>
 * <li>{@link #gravity}:  Konstante mit Wert um Inhalt an den Start des Containers zu "schieben"</li>
 * </ul>
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public class MediumTableDataAdapter extends TableDataAdapter<Medium> {

    private final int paddingLeft = 20;
    private final int paddingTop = 15;
    private final int paddingRight = 20;
    private final int paddingBottom = 15;
    private final int textSize = 18;
    private final int typeface = Typeface.NORMAL;
    private final int textColor = 0x99000000;
    private final int gravity = Gravity.START;

    /**
     * Constructor
     * @param context Context in dem der Adapter platziert werden sollte
     * @param data Daten für die Darstellung
     */
    public MediumTableDataAdapter(Context context, Medium[] data) {
        super(context, data);
    }

    /**
     * Constructor
     * @param context Context in dem der Adapter platziert werden sollte
     * @param data Daten für die Darstellung
     */
    public MediumTableDataAdapter(Context context, List<Medium> data) {
        super(context, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final TextView textView = new TextView(getContext());
        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        textView.setTypeface(textView.getTypeface(), typeface);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        Medium medium = this.getData().get(rowIndex);
        String textToShow = "";
        try {
            if (columnIndex == 0)
                textToShow = medium.getId().toString();
            else if (columnIndex == 1)
                textToShow = medium.getTitel();
            else if (columnIndex == 2)
                textToShow = medium.getAutor();
            textView.setText(textToShow);
            textView.setGravity(gravity);
        } catch (final NullPointerException | IndexOutOfBoundsException e) {
            Log.w(this.getClass().getName(), "No String given for row " + rowIndex + ", column " + columnIndex + ". "
                    + "Caught exception: " + e.toString());
            // Show no text
        }
        return textView;
    }

}
