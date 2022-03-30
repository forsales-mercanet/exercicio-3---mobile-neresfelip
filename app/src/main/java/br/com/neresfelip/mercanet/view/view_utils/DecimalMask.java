package br.com.neresfelip.mercanet.view.view_utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DecimalMask implements TextWatcher {
    /** Classe que aplica uma máscara monetária a um editText
     * estou usando ela no campo Cotação (et_cotacao) */

    private final EditText editText;

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


        if (s.toString().isEmpty()) return;

        editText.removeTextChangedListener(this);

        String cleanString = s.toString();

        if (count != 0) {
            String substr = cleanString.substring(cleanString.length() - 1);

            if (substr.contains(".") || substr.contains(",")) {
                cleanString += "0";
            }
        }

        cleanString = cleanString.replaceAll("[,.]", "");

        double parsed = Double.parseDouble(cleanString);
        DecimalFormat df = new DecimalFormat("0.00");
        String formatted = df.format((parsed / 100));

        formatted = formatted.replace(",", ".");

        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);

    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    public void afterTextChanged(Editable s) {}

}
