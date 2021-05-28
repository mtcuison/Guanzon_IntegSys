package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData;

import android.os.Environment;
import android.util.Log;

import org.rmj.gocas.base.GOCASApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class GOCASExport {
    private static final String TAG = GOCASExport.class.getSimpleName();

    public void WriteJson(String GOCas, String TransNox, String GOCasNox) throws IOException {
        GOCASApplication loGoCas = new GOCASApplication();
        loGoCas.setData(GOCas);
        String appFolder = Environment.getExternalStorageDirectory() + "/Android/data/org.rmj.g3AndroidApplication.ExportedJSON";
        File loFolder = new File(appFolder);
        if(!loFolder.exists()) {
            if(loFolder.mkdir()) {
                Log.e(TAG, "File folder created.");
            } else {
                Log.e(TAG, "Failed to create file folder.");
            }
        }
        String lsFileName = Environment.getExternalStorageDirectory() + "/Android/data/org.rmj.g3AndroidApplication.ExportedJSON/" + TransNox + "-" + GOCasNox + ".json";
        java.io.FileWriter loWriter = new java.io.FileWriter(lsFileName);
        BufferedWriter writer = new BufferedWriter(loWriter);
        String lsGOCas = String.valueOf(loGoCas.toJSON());
        loWriter.flush();
        writer.write(lsGOCas);
        writer.close();
    }
}
