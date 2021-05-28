package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import org.rmj.g3appdriver.dev.AppData;

import java.util.ArrayList;

public class AutoSuggestAddress {

    private AppData db;
    private ArrayList<ArrayList<String>> arr_data;
    private ArrayList<String> arr_id;
    private ArrayList<String> arr_name;

    public AutoSuggestAddress(Context context){
        this.db = AppData.getInstance(context);
    }

    public ArrayList<ArrayList<String>> getProvinceList(){
        try{
            arr_data = new ArrayList<>();
            arr_id = new ArrayList<>();
            arr_name = new ArrayList<>();
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM CreditApp_Province",  null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++){
                    arr_id.add(cursor.getString(cursor.getColumnIndex("sProvIDxx")));
                    arr_name.add(cursor.getString(cursor.getColumnIndex("sProvName")));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        arr_data.add(arr_id);
        arr_data.add(arr_name);
        return arr_data;
    }

    public ArrayList<ArrayList<String>> getTownList(String ProvID){
        try{
            arr_data = new ArrayList<>();
            arr_id = new ArrayList<>();
            arr_name = new ArrayList<>();
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM CreditApp_Town WHERE sProvIDxx = '" + ProvID + "'",  null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++){
                    arr_id.add(cursor.getString(cursor.getColumnIndex("sTownIDxx")));
                    arr_name.add(cursor.getString(cursor.getColumnIndex("sTownName")));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        arr_data.add(arr_id);
        arr_data.add(arr_name);
        return arr_data;
    }

    public ArrayList<ArrayList<String>> getBarangayList(String TownID){
        try{
            arr_data = new ArrayList<>();
            arr_id = new ArrayList<>();
            arr_name = new ArrayList<>();
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM CreditApp_Barangay WHERE sTownIDxx = '" + TownID + "'",  null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++){
                    arr_id.add(cursor.getString(cursor.getColumnIndex("sBrgyIDxx")));
                    arr_name.add(cursor.getString(cursor.getColumnIndex("sBrgyName")));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        arr_data.add(arr_id);
        arr_data.add(arr_name);
        return arr_data;
    }

    public ArrayList<ArrayList<String>> getNationalityList(){
        ArrayList<String> arr_country = new ArrayList<>();
        try{
            arr_data = new ArrayList<>();
            arr_id = new ArrayList<>();
            arr_name = new ArrayList<>();
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM CreditApp_Country",  null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++){
                    arr_id.add(cursor.getString(cursor.getColumnIndex("sCntryCde")));
                    arr_name.add(cursor.getString(cursor.getColumnIndex("sNational")));
                    arr_country.add(cursor.getString(cursor.getColumnIndex("sCntryNme")));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        arr_data.add(arr_id);
        arr_data.add(arr_name);
        arr_data.add(arr_country);
        return arr_data;
    }
}
