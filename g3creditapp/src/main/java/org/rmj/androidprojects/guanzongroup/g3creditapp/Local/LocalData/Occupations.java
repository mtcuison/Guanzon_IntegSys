package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData;

import android.content.Context;
import android.database.Cursor;

import org.rmj.g3appdriver.dev.AppData;

import java.util.ArrayList;

public class Occupations {

    private AppData db;

    public Occupations(Context context){
        this.db = AppData.getInstance(context);
    }

    public ArrayList<ArrayList<String>> getJobTitles(){
        ArrayList<ArrayList<String>> arr_data = new ArrayList<>();
        ArrayList<String> arr_id = new ArrayList<>();
        ArrayList<String> arr_desc = new ArrayList<>();
        try{
            Cursor cursor = db.getReadableDb().rawQuery("SELECT sOccptnID, sOccptnNm " +
                                            "FROM CreditApp_Occupations", null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                for(int x = 0; x < cursor.getCount(); x++){
                    arr_id.add(cursor.getString(cursor.getColumnIndex("sOccptnID")));
                    arr_desc.add(cursor.getString(cursor.getColumnIndex("sOccptnNm")));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        arr_data.add(arr_id);
        arr_data.add(arr_desc);
        return arr_data;
    }

    public ArrayList<ArrayList<String>> getOFWCountry(){
        ArrayList<ArrayList<String>> arr_data = new ArrayList<>();
        ArrayList<String> arr_id = new ArrayList<>();
        ArrayList<String> arr_desc = new ArrayList<>();
        try {
            Cursor cursor = db.getReadableDb().rawQuery("SELECT sCntryCde, sCntryNme " +
                                            "FROM CreditApp_Country " +
                                            "WHERE sCntryCde != '01'", null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                for(int x = 0; x < cursor.getCount(); x++){
                    arr_id.add(cursor.getString(0));
                    arr_desc.add(cursor.getString(1));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        arr_data.add(arr_id);
        arr_data.add(arr_desc);
        return arr_data;
    }
}
