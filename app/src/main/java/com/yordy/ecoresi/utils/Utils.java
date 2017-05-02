package com.yordy.ecoresi.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Usuario on 29/04/2017.
 */

public class Utils {

    public static String formatDate(String fecha){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
        Date d = null;
        try {
            d = formatter.parse(fecha);
            return formatter1.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return fecha;
        }

    }
}
