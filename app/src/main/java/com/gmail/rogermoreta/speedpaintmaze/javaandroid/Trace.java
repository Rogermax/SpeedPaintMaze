package com.gmail.rogermoreta.speedpaintmaze.javaandroid;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

public class Trace {

    public static final String LOG_TAG = "Trace";
    public static File traceFile;

    public static void write(String str) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String hour = addZeros(cal.get(Calendar.HOUR), 2);
        String minuts = addZeros(cal.get(Calendar.MINUTE), 2);
        String seconds = addZeros(cal.get(Calendar.SECOND), 2);
        String miliseconds = addZeros(cal.get(Calendar.MILLISECOND), 3);
        String nameFile = month + ":" + day + ":" + year + ".txt";
        Writer writer = null;
        if (traceFile == null || !traceFile.getName().equals(nameFile)) {
            traceFile = new File(getDownloadStorageDir("Burbujita").getAbsolutePath() + "/" + nameFile);
        }
        try {
            writer = new BufferedWriter(new FileWriter(traceFile, true));
            writer.write(hour + ":" + minuts + ":" + seconds + ":" + miliseconds + "::" + str + "\n");
        } catch (Exception ignored) {
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch (Exception ignored) {
            }
        }
    }

    private static String addZeros(int i, int i1) {
        String numero = String.valueOf(i);
        while (numero.length() < i1) {
            numero = "0" + numero;
        }
        return numero;
    }

    private static File getDownloadStorageDir(String downloadNameDir) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), downloadNameDir);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

}
