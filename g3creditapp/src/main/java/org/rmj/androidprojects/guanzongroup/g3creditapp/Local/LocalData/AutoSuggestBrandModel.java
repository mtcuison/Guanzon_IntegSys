package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import org.rmj.g3appdriver.dev.AppData;

import java.util.ArrayList;

public class AutoSuggestBrandModel {

    private Context mContext;
    private AppData db;
    private ArrayList<ArrayList<String>> data;
    private ArrayList arr_id;
    private ArrayList arr_name;

    public AutoSuggestBrandModel(Context context){
        this.mContext = context;
        this.db = AppData.getInstance(mContext);
    }

    public ArrayList<ArrayList<String>> getBrandList(){
        data = new ArrayList<>();
        arr_id = new ArrayList();
        arr_name = new ArrayList();
        try {
            db.getReadableDb().beginTransaction();
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM MC_Brand WHERE cRecdStat = '1'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++) {
                    arr_id.add(cursor.getString(cursor.getColumnIndex("sBrandIDx")));
                    arr_name.add(cursor.getString(cursor.getColumnIndex("sBrandNme")));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            db.getReadableDb().setTransactionSuccessful();
            db.getReadableDb().endTransaction();
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        data.add(arr_id);
        data.add(arr_name);
        return data;
    }

    public ArrayList<ArrayList<String>> getModelList(String BrandID){
        data = new ArrayList<>();
        arr_id = new ArrayList();
        arr_name = new ArrayList();
        try {
            db.getReadableDb().beginTransaction();
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM MC_Model WHERE sBrandIDx = '" + BrandID + "' AND cRecdStat = '1'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++) {
                    arr_id.add(cursor.getString(cursor.getColumnIndex("sModelIDx")));
                    arr_name.add(cursor.getString(cursor.getColumnIndex("sModelNme")) + "  (" +
                            cursor.getString(cursor.getColumnIndex("sModelCde")) +")");
                    cursor.moveToNext();
                }
            }
            cursor.close();
            db.getReadableDb().setTransactionSuccessful();
            db.getReadableDb().endTransaction();
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        data.add(arr_id);
        data.add(arr_name);
        return data;
    }

    public ArrayList<ArrayList<String>> getMCBranchesList(){
        data = new ArrayList<>();
        arr_id = new ArrayList();
        arr_name = new ArrayList();
        try {
            db.getReadableDb().beginTransaction();
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM CreditApp_Branches WHERE cDivision = '1'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++) {
                    arr_id.add(cursor.getString(cursor.getColumnIndex("sBranchCd")));
                    arr_name.add(cursor.getString(cursor.getColumnIndex("sBranchNm")));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            db.getReadableDb().setTransactionSuccessful();
            db.getReadableDb().endTransaction();
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        data.add(arr_id);
        data.add(arr_name);
        return data;
    }

    public ArrayList<ArrayList<String>> getMPBranchesList(){
        data = new ArrayList<>();
        arr_id = new ArrayList();
        arr_name = new ArrayList();
        try {
            db.getReadableDb().beginTransaction();
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM CreditApp_Branches WHERE cDivision = '0'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++) {
                    arr_id.add(cursor.getString(cursor.getColumnIndex("sBranchCd")));
                    arr_name.add(cursor.getString(cursor.getColumnIndex("sBranchNm")));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            db.getReadableDb().setTransactionSuccessful();
            db.getReadableDb().endTransaction();
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        data.add(arr_id);
        data.add(arr_name);
        return data;
    }
}
