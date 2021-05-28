package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.GenerateTransNox;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.gocas.base.GOCASApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreditApplication {
    private static final String TAG = CreditApplication.class.getSimpleName();

    private Context mContext;
    private OnSaveGoCasApplicationListener poListener;
    private String DateCreated;
    private org.rmj.g3appdriver.dev.AppData db;

    public CreditApplication(Context context){
        this.mContext = context;
        this.db = org.rmj.g3appdriver.dev.AppData.getInstance(mContext);
    }

    public interface OnNewApplicationCreateListener{
        void OnCreateApplication(String TransNox);
        void OnError(String message);
    }

    public interface OnSaveGoCasApplicationListener{
        void OnSaveApplication(String TransNox);
    }

    public interface OnDeleteApplicationListener{
        void OnDelete(String Message);
    }

    /**
     *
     * @param listener
     * @param GoCas
     * a new credit application is going to be created.
     * listener returns the instance of GoCas
     */
    public void NewCreditApplication(GOCASApplication GoCas, String DateCreated, OnNewApplicationCreateListener listener){
        try {
            String transno = new GenerateTransNox(mContext).getTempTransNox();
            String lsUserID = getUserID();
            String lsGoCas = GoCas.toJSONString();
            Log.e(TAG, "Applicant information result : " + GoCas.toJSONString());
            String[] bindArgs = {transno, GoCas.PurchaseInfo().getPreferedBranch(), DateCreated, lsUserID, lsGoCas, "0", DateCreated};
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("INSERT INTO Temp_CreditApplication(" +
                    "sTransNox, " +
                    "sBranchCd, " +
                    "dCreatedx, " +
                    "sCreatedx, " +
                    "sDetlInfo, " +
                    "cApplStat, " +
                    "dModified)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)", bindArgs);
            db.getWritableDb().setTransactionSuccessful();
            Log.e(TAG, "New credit application has been created.");
            listener.OnCreateApplication(transno);
        } catch (Exception e){
            Log.e(TAG, "Unable to create credit application. Error : " + e.getMessage());
            e.printStackTrace();
            listener.OnError(e.getMessage());
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    /**
     *
     * @param TransNox
     * @return GOCASApplication
     * this returns the current active credit application
     * GoCas info came from the field sDetlInfo of Sqlite
     * Database which has the information from the other forms.
     */
    public GOCASApplication getActiveGoCasInfo(String TransNox){
        try{
            Cursor cursor = db.getReadableDb().rawQuery("SELECT sDetlInfo FROM Temp_CreditApplication WHERE sTransNox = '"+TransNox+ "'", null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                String lsDetails = cursor.getString(0);
                GOCASApplication loGoCas = new GOCASApplication();
                loGoCas.setData(lsDetails);
                cursor.close();
                return loGoCas;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param ApplicationInfo
     * @param TransNox
     *
     * call this method to update the information
     * of GoCas on LocalDatabase...
     * Pass the current TransNox of the current credit
     * application to specify which information is going
     * to update...
     */
    public void UpdateApplicationInfo(GOCASApplication ApplicationInfo, String TransNox){
        try{
            String lsGoCas = ApplicationInfo.toJSONString();
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("UPDATE Temp_CreditApplication SET sDetlInfo = '"+lsGoCas+"', " +
                    "sClientNm = '"+ApplicationInfo.ApplicantInfo().getClientName()+"', " +
                    "cUnitAppl = '"+ApplicationInfo.PurchaseInfo().getAppliedFor()+"' " +
                    "WHERE sTransNox = '"+TransNox+"'");
            Log.e(TAG, "Local GoCas info has been updated.");
            db.getWritableDb().setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    /**
     *
     * @param ApplicationInfo GOCas Application
     * @param TransNox Transaction no of current on going application
     * @param listener
     *
     * this method transfers GoCasApplication data to
     * Credit_Online_Application table.
     * if the current device has connection on
     * GoCas application will automatically send to server ...240
     *
     * TransNox value from the Temp_CreditApplication differs from
     * Credit_Online_Application.
     * TransNox - parameter must be the value of TransNox From Temp_CreditApplication
     * OnSaveGoCasApplicationListener - returns the TransNox value of Credit_Online_Application table
     */
    public void saveFinalUpdate(GOCASApplication ApplicationInfo, String TransNox, OnSaveGoCasApplicationListener listener){
        poListener = listener;
        try{
            String[] bindArgs = {TransNox};
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("UPDATE Temp_CreditApplication " +
                    "SET sDetlInfo = '"+ApplicationInfo.toJSONString()+"', " +
                    "cApplStat = '1' " +
                    "WHERE sTransNox = ?", bindArgs);
            db.getWritableDb().setTransactionSuccessful();
            db.getWritableDb().endTransaction();
            Log.e(TAG, "Local GoCas info has been updated.");
            Cursor cursor = db.getWritableDb().rawQuery("SELECT dCreatedx FROM Temp_CreditApplication WHERE sTransNox = ?", bindArgs);
            cursor.moveToFirst();
            DateCreated = cursor.getString(0);
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        saveToCreditOnlineApplication(getFinalGoCasData(TransNox));
    }

    private GOCASApplication getFinalGoCasData(String TransNox){
        String lsGoCas;
        db.getReadableDb();
        Cursor loCursor = db.getReadableDb().rawQuery("SELECT * FROM Temp_CreditApplication " +
                "WHERE sTransNox = '"+TransNox+"'", null);
        if(loCursor.getCount() > 0){
            loCursor.moveToFirst();
            lsGoCas = loCursor.getString(loCursor.getColumnIndex("sDetlInfo"));
            GOCASApplication loGoCas = new GOCASApplication();
            loGoCas.setData(lsGoCas);
            loCursor.close();
            return loGoCas;
        }
        loCursor.close();
        return null;
    }

    /**
     *
     * @param gocas
     * pass the final result of GoCas object.
     * this method is to transfer the final data to the
     * Credit_Online_Application.
     * After saving the final data to Credit_Online_Application
     * send the GoCas object to server.
     */
    private void saveToCreditOnlineApplication(GOCASApplication gocas){
        String lsTransno = "";
        try {
            lsTransno = new GenerateTransNox(mContext).getLocalTransNox();
            String lsUserID = getUserID();
            db.getWritableDb().beginTransaction();
            String[] bindArgs = {
                    lsTransno,
                    gocas.PurchaseInfo().getPreferedBranch(),
                    getDateTransact(),
                    "",
                    gocas.ApplicantInfo().getClientName(),
                    "",
                    "",
                    gocas.PurchaseInfo().getAppliedFor(),
                    "APP",
                    gocas.toJSONString(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    String.valueOf(gocas.PurchaseInfo().getDownPayment()),
                    "",
                    "",
                    lsUserID,
                    DateCreated,
                    "0",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "0",
                    ""};
            db.getWritableDb().execSQL("INSERT INTO Credit_Online_Application (" +
                    "sTransNox, " +
                    "sBranchCd, " +
                    "dTransact, " +
                    "dTargetDt, " +
                    "sClientNm, " +
                    "sGOCASNox, " +
                    "sGOCASNoF, " +
                    "cUnitAppl, " +
                    "sSourceCD, " +
                    "sDetlInfo, " +
                    "sCatInfox, " +
                    "sDesInfox, " +
                    "sQMatchNo, " +
                    "sQMAppCde, " +
                    "nCrdtScrx, " +
                    "nDownPaym, " +
                    "nDownPayF, " +
                    "sRemarksx, " +
                    "sCreatedx, " +
                    "dCreatedx, " +
                    "cSendStat, " +
                    "dReceived, " +
                    "sVerified, " +
                    "dVerified, " +
                    "cTranStat, " +
                    "cDivision, " +
                    "cApplStat, " +
                    "dModified)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", bindArgs);
            Log.e(TAG, "Credit score information result : " + gocas.toJSONString());
            Log.e(TAG, "Credit application result : Android credit application has been save.");
            db.getWritableDb().setTransactionSuccessful();
        } catch (Exception e){
            Log.e(TAG, "Credit application result : Unable to save credit online application.");
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
        poListener.OnSaveApplication(lsTransno);
    }

    private String getDateTransact(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    private String getUserID(){
        return AppData.getInstance(mContext).getUserID();
    }

    public void CancelApplication(String TransNox, OnDeleteApplicationListener listener){
        String message;
        try{
            String[] bindArgs = {TransNox};
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("DELETE FROM Temp_CreditApplication WHERE sTransNox = ?", bindArgs);
            message = "Credit Application has been canceled.";
            db.getWritableDb().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            message = "Something went wrong. Please contact MIS";
        } finally {
            db.getWritableDb().endTransaction();
        }
        listener.OnDelete(message);
    }

    public void voidApplication(String transnox, OnDeleteApplicationListener listener){
        try{
            String[] bindArgs = {transnox};
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("DELETE FROM Credit_Online_Application WHERE sTransNox = ?", bindArgs);
            db.getWritableDb().setTransactionSuccessful();
            listener.OnDelete("Application has been void successfully.");
        } catch (Exception e){
            e.printStackTrace();
            listener.OnDelete("Unable to void application.");
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    public GOCASApplication getGOCasInfoForCI(String TransNox){
        try{
            String[] bindArgs = {TransNox};
            Cursor loCursor = db.getReadableDb().rawQuery("SELECT * FROM Credit_Online_Application WHERE sTransNox = ?", bindArgs);
            if(loCursor.getCount() > 0){
                loCursor.moveToFirst();
                String lsGOCas = loCursor.getString(loCursor.getColumnIndex("sDetlInfo"));
                GOCASApplication loGoCas = new GOCASApplication();
                loGoCas.setData(lsGOCas);
                loCursor.close();
                return loGoCas;
            }
            loCursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
