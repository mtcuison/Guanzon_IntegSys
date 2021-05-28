package org.rmj.androidprojects.guanzongroup.g3creditapp.Etc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.rmj.appdriver.mob.base.GRider;
import org.rmj.appdriver.mob.base.MiscUtil;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.etc.SharedPref;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateTransNox {
    private static final String TAG = GenerateTransNox.class.getSimpleName();

    //private AppData db;
    private Context mContext;

    public GenerateTransNox(Context context){
        this.mContext = context;
        //this.db = AppData.getInstance(context);
    }

    @SuppressLint("DefaultLocale")
    public String getTempTransNox(){
        return getNextUserTransNo("Temp_CreditApplication");

        /*Cursor loCursor = db.getReadableDb().rawQuery("SELECT * FROM Temp_CreditApplication", null);
        int liCount = loCursor.getCount();
        loCursor.close();
        Log.e(TAG, "local transaction number has been created." + getUserAcronym() + getDate() + String.format("%04d", liCount));
        return getUserAcronym() + getDate() + String.format("%04d", liCount);*/
    }

    @SuppressLint("DefaultLocale")
    public String getLocalTransNox(){
        return getNextUserTransNo("Credit_Online_Application");
        /*Cursor loCursor = db.getReadableDb().rawQuery("SELECT * FROM Credit_Online_Application", null);
        int liCount = loCursor.getCount();
        loCursor.close();
        Log.e(TAG, "local transaction number has been created." + getUserAcronym() + getDate() + String.format("%04d", liCount));
        return getUserAcronym() + getDate() + String.format("%04d", liCount);*/
    }
    /*
    private String getUserAcronym() {
        String lsUserName = db.getUserName();
        String[] laSplitString = lsUserName.split(" ");
        String lcAcro1 = String.valueOf(laSplitString[0].charAt(0));
        String lcAcro2 = String.valueOf(laSplitString[1].charAt(0));
        String lcAcro3 = String.valueOf(laSplitString[2].charAt(0));
        return lcAcro1 + lcAcro2 + lcAcro3;
    }

    @SuppressLint("SimpleDateFormat")
    private String getDate(){
        Date loDate = new Date();
        SimpleDateFormat loFormater = new SimpleDateFormat("MMDD");
        return loFormater.format(loDate);
    }*/

    private String getNextUserTransNo(String fsTableName){
        //validate table name
        if (fsTableName.isEmpty()) return "";

        //connect to appdriver mobile
        //pass the application package name to initialize object.
        //the database name is always "GGC_ISysDBF.db" so no need to pass on the object
        GRider loApp = new GRider("org.rmj.guanzongroup.integsys", "");
        if (!loApp.loadEnv()) return "";

        //i want to get the current user id. so i execute a query to the database using appdriver connection
        //START (get user id)
        String lsSQL = "SELECT sUserIDxx FROM ";
        SharedPref loPref = new SharedPref(mContext);
        if(loPref.ProducID().equalsIgnoreCase("GuanzonApp")){
            lsSQL = lsSQL + "Client_Info_Master";
        } else {
            lsSQL = lsSQL + "User_Info_Master";
        }

        String lsUserID = "";
        ResultSet loRS = loApp.executeQuery(lsSQL);
        try {
            if (!loRS.next()) return "";

            lsUserID = loRS.getString("sUserIDxx");
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        } finally {
            //close resultset to save memory usage.
            MiscUtil.close(loRS);
        }
        //END (get user id)

        if (lsUserID.isEmpty()) return "";

        loApp.setUserID(lsUserID);
        lsUserID = loApp.getUserCode();

        lsSQL = MiscUtil.getNextCode(fsTableName, "sTransNox", true, loApp.getConnection(), lsUserID, 12, false);

        //destroy appdriver object to save memory usage.
        loApp = null;

        return lsSQL;
    }
}
