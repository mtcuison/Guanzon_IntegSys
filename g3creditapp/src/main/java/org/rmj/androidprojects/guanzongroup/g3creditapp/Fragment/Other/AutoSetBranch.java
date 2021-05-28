package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Other;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.rmj.g3appdriver.dev.AppData;

public class AutoSetBranch {
    private static final String TAG = AutoSetBranch.class.getSimpleName();

    private AppData db;
    private Cursor cursor;

    public AutoSetBranch(Context context){
        this.db = AppData.getInstance(context);
    }

    private String getBranchInfo(String PsFieldName){
        String lsBranchCde = getUserBranchID();
        try {
            String[] args = {lsBranchCde};
            db.getReadableDb().beginTransaction();
            cursor = db.getReadableDb().rawQuery("SELECT * FROM CreditApp_Branches " +
                    "WHERE sBranchCd = ?", args);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                String lsReturn = cursor.getString(cursor.getColumnIndex(PsFieldName));
                cursor.close();
                db.getReadableDb().setTransactionSuccessful();
                db.getReadableDb().endTransaction();
                Log.e(TAG, "Branch data has been retrieve. " + lsReturn);
                return lsReturn;
            }
            cursor.close();
            db.getReadableDb().setTransactionSuccessful();
            db.getReadableDb().endTransaction();
            Log.e(TAG, "No branch data has been retrieve.");
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * @return
     * this returns the value of Branch Code where
     * the user or employee is employed.
     */
    public String getBranchCode(){
        return getBranchInfo("sBranchCd");
    }

    /**
     *
     * @
     * this return s the value of Branch  where
     * the user or employee is employed.
     */
    public String getBranchName(){
        return getBranchInfo("sBranchNm");
    }

    private String getUserBranchID(){
        String BranchCde = "";
        try {
            db.getReadableDb().beginTransaction();
            cursor = db.getReadableDb().rawQuery("SELECT * FROM User_Info_Master", null);
            cursor.moveToFirst();
            BranchCde = cursor.getString(cursor.getColumnIndex("sBranchCD"));
            cursor.close();
            db.getReadableDb().setTransactionSuccessful();
            db.getReadableDb().endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BranchCde;
    }
}
