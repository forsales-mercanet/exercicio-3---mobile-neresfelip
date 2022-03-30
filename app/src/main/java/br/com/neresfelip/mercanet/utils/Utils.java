package br.com.neresfelip.mercanet.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Utils {

    /** Apenas um conversor de double pra string */
    public static String doubleToDecimalString(double valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return df.format(valor);
    }

}
