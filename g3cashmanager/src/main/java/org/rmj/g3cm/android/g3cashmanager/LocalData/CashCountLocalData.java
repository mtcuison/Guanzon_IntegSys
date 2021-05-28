package org.rmj.g3cm.android.g3cashmanager.LocalData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.rmj.g3appdriver.dev.AppData;

import java.util.ArrayList;
import java.util.List;

public class CashCountLocalData {
    private static final String TAG = CashCountLocalData.class.getSimpleName();
    private AppData db;

    public CashCountLocalData(Context context){
        this.db = AppData.getInstance(context);
    }

    public void setupCashCountData() {
        try {
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS Cash_Count_Master (" +
                    "sTransNox varchar, " +
                    "dTransact varchar, " +
                    "nCn0001cx varchar, " +
                    "nCn0005cx varchar," +
                    "nCn0025cx varchar, " +
                    "nCn0001px varchar, " +
                    "nCn0005px varchar, " +
                    "nCn0010px varchar, " +
                    "nNte0020p varchar, " +
                    "nNte0050p varchar, " +
                    "nNte0100p varchar, " +
                    "nNte0200p varchar, " +
                    "nNte0500p varchar, " +
                    "nNte1000p varchar, " +
                    "sORNoxxxx varchar, " +
                    "sSINoxxxx varchar, " +
                    "sPRNoxxxx varchar, " +
                    "sCRNoxxxx varchar, " +
                    "dEntryDte varchar, " +
                    "dReceived varchar, " +
                    "sReqstdBy varchar, " +
                    "sModified varchar, " +
                    "dModified varchar," +
                    "dTimeStmp DATETIME DEFAULT CURRENT_TIMESTAMP)");
            Log.e(TAG, "Local database table for cash count has been created.");
            db.getWritableDb().setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    public void InsertCashCountData(CashCount cashCount, OnCashCountSaveListener listener){
        try{
            String lsSql = "INSERT INTO Cash_Count_Master(" +
                    " sTransNox " +
                    ", dTransact " +
                    ", nCn0001cx " +
                    ", nCn0005cx " +
                    ", nCn0025cx " +
                    ", nCn0001px " +
                    ", nCn0005px " +
                    ", nCn0010px " +
                    ", nNte0020p " +
                    ", nNte0050p " +
                    ", nNte0100p " +
                    ", nNte0200p " +
                    ", nNte0500p " +
                    ", nNte1000p " +
                    ", sORNoxxxx " +
                    ", sSINoxxxx " +
                    ", sPRNoxxxx " +
                    ", sCRNoxxxx " +
                    ", dEntryDte " +
                    ", dReceived " +
                    ", sReqstdBy " +
                    ", sModified " +
                    ", dModified) VALUES (" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            SQLiteStatement loSql = db.getWritableDb().compileStatement(lsSql);
            db.getWritableDb().beginTransaction();
            loSql.bindString(1, cashCount.getTransNox());
            loSql.bindString(2, cashCount.getTransNox());
            loSql.bindString(3, cashCount.getTransNox());
            loSql.bindString(4, cashCount.getTransNox());
            loSql.bindString(5, cashCount.getTransNox());
            loSql.bindString(6, cashCount.getTransNox());
            loSql.bindString(7, cashCount.getTransNox());
            loSql.bindString(8, cashCount.getTransNox());
            loSql.bindString(9, cashCount.getTransNox());
            loSql.bindString(10, cashCount.getTransNox());
            loSql.bindString(12, cashCount.getTransNox());
            loSql.bindString(13, cashCount.getTransNox());
            loSql.bindString(14, cashCount.getTransNox());
            loSql.bindString(15, cashCount.getTransNox());
            loSql.bindString(16, cashCount.getTransNox());
            loSql.bindString(17, cashCount.getTransNox());
            loSql.bindString(18, cashCount.getTransNox());
            loSql.bindString(19, cashCount.getTransNox());
            loSql.bindString(20, cashCount.getTransNox());
            loSql.bindString(21, cashCount.getTransNox());
            loSql.bindString(22, cashCount.getTransNox());
            loSql.bindString(23, cashCount.getTransNox());
            loSql.execute();
            db.getWritableDb().setTransactionSuccessful();
            listener.OnSave(cashCount.getTransNox());
        } catch (Exception e){
            e.printStackTrace();
            listener.OnFailed(e.getMessage());
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    public List<CashCountLog> getCashCountList(){
        List<CashCountLog> cashCounts = new ArrayList<>();
        try{
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM Cash_Count_Master", null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                for(int x = 0; x < cursor.getCount(); x ++){
                    CashCountLog loCashCountLog = new CashCountLog();
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("sTransNox")));
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("dTransact")));
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("sORNoxxxx")));
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("sSINoxxxx")));
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("sPRNoxxxx")));
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("sCRNoxxxx")));
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("dEntryDte")));
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("dReceived")));
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("sReqstdBy")));
                    loCashCountLog.setTransNox(cursor.getString(cursor.getColumnIndex("sModified")));
                    cashCounts.add(loCashCountLog);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cashCounts;
    }

    public interface OnCashCountSaveListener{
        void OnSave(String TransNox);
        void OnFailed(String message);
    }
}
