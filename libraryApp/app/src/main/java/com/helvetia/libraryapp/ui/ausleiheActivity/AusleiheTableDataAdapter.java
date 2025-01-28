package com.helvetia.libraryapp.ui.ausleiheActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helvetia.libraryapp.ui.ausleiheActivity.AusleiheTableActivity;
import com.helvetia.libraryapp.common.items.Ausleihe;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Klasse zur Darstellung der Daten in der Tabelle der Klasse {@link AusleiheTableActivity} <br>
 * <strong>Attribute:</strong>
 * <ul>
 * <li>{@link #PADDING_LEFT}: Konstante mit Wert für linkes Padding</li>
 * <li>{@link #PADDING_TOP}: Konstante mit Wert für oberes Padding</li>
 * <li>{@link #PADDING_RIGHT}: Konstante mit Wert für rechtes Padding</li>
 * <li>{@link #PADDING_BOTTOM}: Konstante mit Wert für unteres Padding</li>
 * <li>{@link #TEXTSIZE}: Konstante mit Wert für Textgrösse</li>
 * <li>{@link #TYPEFACE}: Konstante mit Wert für den Font</li>
 * <li>{@link #TEXTCOLOR}: Konstante mit Wert für Textfarbe</li>
 * <li>{@link #GRAVITY}:  Konstante mit Wert um Inhalt an den Start des Containers zu "schieben"</li>
 * </ul>
 *
 * @version 1.0.0
 * @author Simon Fäs
 */
public class AusleiheTableDataAdapter extends TableDataAdapter<Ausleihe> {

    private final int PADDING_LEFT = 20;
    private final int PADDING_TOP = 15;
    private final int PADDING_RIGHT = 20;
    private final int PADDING_BOTTOM = 15;
    private final int TEXTSIZE = 18;
    private final int TYPEFACE = Typeface.NORMAL;
    private final int TEXTCOLOR = 0x99000000;
    private final int GRAVITY = Gravity.START;

    /**
     * Constructor
     * @param context Context in dem der Adapter platziert werden sollte
     * @param data Daten für die Darstellung
     */
    public AusleiheTableDataAdapter(Context context, Ausleihe[] data) {
        super(context, data);
    }

    /**
     * Constructor
     * @param context Context in dem der Adapter platziert werden sollte
     * @param data Daten für die Darstellung
     */
    public AusleiheTableDataAdapter(Context context, List<Ausleihe> data) {
        super(context, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final TextView textView = new TextView(getContext());
        textView.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
        textView.setTypeface(textView.getTypeface(), TYPEFACE);
        textView.setTextSize(TEXTSIZE);
        textView.setTextColor(TEXTCOLOR);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        Ausleihe ausleihe = this.getData().get(rowIndex);
        String textToShow = "";
        try {
            if (columnIndex == 0)
                textToShow = ausleihe.getMedium().getId().toString();
            else if (columnIndex == 1) {
                textToShow = ausleihe.getKunde().getId().toString();
            } else if (columnIndex == 2)
                textToShow = ausleihe.returnDateAsString();
            textView.setText(textToShow);
            textView.setGravity(GRAVITY);
        } catch (final NullPointerException | IndexOutOfBoundsException e) {
            Log.w(this.getClass().getName(), "No String given for row " + rowIndex + ", column " + columnIndex + ". "
                    + "Caught exception: " + e.toString());
        }
        return textView;
    }

}
