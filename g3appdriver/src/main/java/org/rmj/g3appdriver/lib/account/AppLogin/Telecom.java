package org.rmj.g3appdriver.lib.account.AppLogin;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.SQLUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Telecom implements G3AppsLogin {
    private static final String TAG = Telecom.class.getSimpleName();
    private static final String GUANZONAPP_LOGIN = "https://restgk.guanzongroup.com.ph/security/mlogin.php";
    private AppData appData;
    private JSONObject params;
    private SharedPref sharedPref;

    /**
     * Use mResult String Value to indicate
     * the actual result of sending request
     * the to server...*/
    private String mResult = "";

    /**
     * Use mMessage String value to indicate
     * the actual message of error result
     * after unsuccessful request to the
     * server...*/
    private String mMessage = "";

    @Override
    public void loginUser(Context context, final HashMap headers, final JSONObject Parameters) {
        this.appData = AppData.getInstance(context);
        this.params = Parameters;
        this.sharedPref = new SharedPref(context);
        new HttpRequestUtil().sendRequest(GUANZONAPP_LOGIN, new HttpRequestUtil.onServerResponseListener() {
            @Override
            public HashMap setHeaders() {
                /**
                 * All Api Headers must be pass in this area of code.
                 * headers are compose of parameters need to validate
                 * a device access to the servre for secure connection...*/
                return headers;
            }

            @Override
            public JSONObject setData() {
                /**
                 * Parameters that are going to be send on server via api
                 * must be input in this area of code...
                 * In this situation. Login command only has username and
                 * password parameters..*/


                if(sharedPref.getMobileNo().equalsIgnoreCase("")) {
                    /**
                     * the default value of mobile number in shared_pref is empty...
                     * therefore on 2nd login no mobile number must be set...*/
                    sharedPref.setTemp_mobileno(getMobileNo());
                }

                return Parameters;
            }

            @Override
            public void onResponse(JSONObject jsonResponse) {
                /**
                 * Saving an updating local database must be done in this area...
                 * This area of code only applies when HttpRequest made a successful
                 * request to server and returns a 'success' response..*/

                /**
                 * Pass the JSONObject return parameter to the saveLoginDataToLocal
                 * to save the user info to the local database...*/
               saveLoginEmployeeData(jsonResponse);
            }

            @Override
            public void onErrorResponse(String message) {
                /**
                 * This area indicates and catches the errors
                 * after unsuccessful request from the server.
                 * Message contains the error message coming
                 * from the API or coming from device...*/
                mResult = "error";
                /**
                 * Get String String message Return value
                 * and set to mReturn...
                 * this will return a message to the class
                 * which extends or uses this class.*/
                mMessage = message;
            }
        });
    }

    @Override
    public String getResult() {
        return mResult;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }



    /** Functions below this line are made to save the user's credential to local database...
     * **************************************
     *
     *
     *
     *
     * */
    private void saveLoginEmployeeData(JSONObject foValue){
        try{
            String sClientID = foValue.getString("sClientID");
            String sBranchCD = foValue.getString("sBranchCD");
            String sBranchNm = foValue.getString("sBranchNm");
            String sLogNoxxx = foValue.getString("sLogNoxxx");
            String sUserIDxx = foValue.getString("sUserIDxx");
            String sEmailAdd = foValue.getString("sEmailAdd");
            String sUserName = foValue.getString("sUserName");
            String nUserLevl = foValue.getString("nUserLevl");
            String sDeptIDxx = foValue.getString("sDeptIDxx");
            String sPositnID = foValue.getString("sPositnID");
            String sEmpLevID = foValue.getString("sEmpLevID");
            String cAllowUpd = foValue.getString("cAllowUpd");
            String dLoginxxx = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            Cursor loCursor = appData.getReadableDb().rawQuery("SELECT * FROM User_Info_Master", null);
            int lnRow = loCursor.getCount();
            String lsSQL;
            if(lnRow <= 0){
                appData.getWritableDb().beginTransaction();
                lsSQL = "INSERT INTO  User_Info_Master ( " +
                        "  sClientID" +
                        ", sBranchCD" +
                        ", sBranchNm" +
                        ", sLogNoxxx" +
                        ", sUserIDxx" +
                        ", sEmailAdd" +
                        ", sUserName" +
                        ", nUserLevl" +
                        ", sDeptIDxx" +
                        ", sPositnID" +
                        ", sEmpLevID" +
                        ", cAllowUpd" +
                        ", dLoginxxx" +
                        ", sMobileNo" +
                        ", dSessionx)" +
                        "  VALUES (" +
                        SQLUtil.toSQL(sClientID) +
                        ", " + SQLUtil.toSQL(sBranchCD) +
                        ", " + SQLUtil.toSQL(sBranchNm) +
                        ", " + SQLUtil.toSQL(sLogNoxxx) +
                        ", " + SQLUtil.toSQL(sUserIDxx) +
                        ", " + SQLUtil.toSQL(sEmailAdd) +
                        ", " + SQLUtil.toSQL(sUserName) +
                        ", " + SQLUtil.toSQL(nUserLevl) +
                        ", " + SQLUtil.toSQL(sDeptIDxx) +
                        ", " + SQLUtil.toSQL(sPositnID) +
                        ", " + SQLUtil.toSQL(sEmpLevID) +
                        ", " + SQLUtil.toSQL(cAllowUpd) +
                        ", " + SQLUtil.toSQL(dLoginxxx) +
                        ", '"+ getMobileNo()+"'" +
                        ", DATETIME('now', 'localtime'))";

                appData.getWritableDb().execSQL(lsSQL);
                appData.getWritableDb().setTransactionSuccessful();
                Log.d(TAG, " Action Created: Insert");
            } else{
                appData.getWritableDb().beginTransaction();
                lsSQL = "UPDATE User_Info_Master SET" +
                        "  sClientID = " + SQLUtil.toSQL(sClientID) +
                        ", sBranchCD = " + SQLUtil.toSQL(sBranchCD) +
                        ", sBranchNm = " + SQLUtil.toSQL(sBranchNm) +
                        ", sLogNoxxx = " + SQLUtil.toSQL(sLogNoxxx) +
                        ", sUserIDxx = " + SQLUtil.toSQL(sUserIDxx) +
                        ", sEmailAdd = " + SQLUtil.toSQL(sEmailAdd) +
                        ", sUserName = " + SQLUtil.toSQL(sUserName) +
                        ", nUserLevl = " + SQLUtil.toSQL(nUserLevl) +
                        ", sDeptIDxx = " + SQLUtil.toSQL(sDeptIDxx) +
                        ", sPositnID = " + SQLUtil.toSQL(sPositnID) +
                        ", sEmpLevID = " + SQLUtil.toSQL(sEmpLevID) +
                        ", cAllowUpd = " + SQLUtil.toSQL(cAllowUpd) +
                        ", dSessionx = DATETIME('now', 'localtime') " +
                        ", dLoginxxx = " + SQLUtil.toSQL(dLoginxxx);

                appData.getWritableDb().execSQL(lsSQL);
                appData.getWritableDb().setTransactionSuccessful();
                Log.d(TAG, " Action Created: Update");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Catch Error: " + e.getMessage());
            mMessage = "Error 102: " + "Login Credentials Invalid";
        } finally {
            appData.getWritableDb().endTransaction();
        }
        mResult = "success";
    }

    private String getMobileNo(){
        try{
            return params.getString("nmbr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
