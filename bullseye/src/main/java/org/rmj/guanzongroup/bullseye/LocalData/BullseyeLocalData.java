package org.rmj.guanzongroup.bullseye.LocalData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import org.rmj.g3appdriver.dev.AppData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BullseyeLocalData {
    private AppData db;
    private String message;

    public BullseyeLocalData(Context context){
        this.db = AppData.getInstance(context);
    }

    public String getMessage(){
        return message;
    }

    public void setupBullseyeData(){
        try{
            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS MC_Area_Performance(" +
                    "sAreaCode varchar PRIMARY KEY, " +
                    "sAreaDesc varchar, " +
                    "nMCGoalxx int, " +
                    "nSPGoalxx float, " +
                    "nJOGoalxx int, " +
                    "nLRGoalxx float, " +
                    "nMCActual int, " +
                    "nSPActual float, " +
                    "nLRActual float)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS MC_Branch_Performance(" +
                    "sBranchCd varchar PRIMARY KEY, " +
                    "sBranchNm varchar, " +
                    "nMCGoalxx int, " +
                    "nSPGoalxx float, " +
                    "nJOGoalxx int, " +
                    "nLRGoalxx float, " +
                    "nMCActual int, " +
                    "nSPActual float, " +
                    "nLRActual float)");

            //codes for creating tables for Bullseye

        } catch (SQLiteException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Area_Model> getAreaList(){
        List<Area_Model> listArea = new ArrayList<>();
        try{
            Cursor loCursor = db.getReadableDb().rawQuery("SELECT sAreaCode, sAreaDesc, nMCGoalxx, nMCActual FROM MC_Area_Performance", null);
            if(loCursor.getCount() > 0){
                loCursor.moveToFirst();
                for(int x = 0; x < loCursor.getCount(); x++){
                    Area_Model areaModel = new Area_Model(
                            loCursor.getString(0),
                            loCursor.getString(1),
                            loCursor.getString(2),
                            loCursor.getString(3));
                    listArea.add(areaModel);
                    loCursor.moveToNext();
                }
            }
            db.getReadableDb().setTransactionSuccessful();
            db.getReadableDb().endTransaction();
        } catch (Exception e){
            e.printStackTrace();
        }
        return listArea;
    }

    public List<Branch_Model> getBranchList(String AreaCode) {
        List<Branch_Model> listBranch = new ArrayList<>();
        try {
            String[] args = {AreaCode};
            Cursor loCursor = db.getReadableDb().rawQuery("SELECT a.sBranchCd, a.sBranchNm, a.nMCGoalxx, a.nMCActual, b.sDescript, c.sAreaDesc " +
                    "FROM MC_Branch_Performance a " +
                    "LEFT JOIN CreditApp_Branches b " +
                    "ON a.sBranchCd = b.sBranchCd " +
                    "LEFT JOIN MC_Area_Performance c " +
                    "ON b.sAreaCode = c.sAreaCode " +
                    "WHERE c.sAreaCode = ?", args);
            if (loCursor.getCount() > 0) {
                loCursor.moveToFirst();
                for (int x = 0; x < loCursor.getCount(); x++) {
                    Branch_Model branchModel = new Branch_Model(
                            loCursor.getString(0),
                            loCursor.getString(1),
                            loCursor.getString(2),
                            loCursor.getString(3)
                    );
                    listBranch.add(branchModel);
                    loCursor.moveToNext();
                }
            }
            db.getReadableDb().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBranch;
    }

    public int getBranchDynamicBarSize(){
        Cursor loCursor = db.getReadableDb().rawQuery("SELECT MAX(nMCActual) FROM MC_Branch_Performance", null);
        if(loCursor.getCount() > 0){
            loCursor.moveToFirst();
            float adds = loCursor.getInt(0) * .5f;
            loCursor.close();
            return Integer.parseInt(new DecimalFormat("###").format(100 + adds));
        }
        return 0;
    }
}
