package org.rmj.g3appdriver.lib.account.AppLogin;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.etc.SQLiteHandler;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.SQLUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class GuanzonApps implements G3AppsLogin {
    private static final String TAG = GuanzonApps.class.getSimpleName();
    private static final String GUANZONAPP_LOGIN = "https://restgk.guanzongroup.com.ph/security/signin.php";
    private Context mContext;
    private AppData appData;
    private SharedPref sharedPref;
    private JSONObject params;

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
        this.mContext = context;
        this.appData = AppData.getInstance(context);
        this.params = Parameters;
        sharedPref = new SharedPref(mContext);
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
                 * Paramteres that are going to be send on server via api
                 * mus be input in this area of code...
                 * In this situation. Login command only has username and
                 * password parameters..*/

                /**
                 * First login attemp doesnt have MobileNo stored int the
                 * local database. Therefore in this case we set the mobile
                 * number to the share preferences.
                 * 'mobile' is the key value of the mobile number provided
                 * by the user in the Login UI...
                 * get The Mobile Number using the method getMobileNo
                 * and set the parameter JSONObject*/
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
                 * Pass the JSONObject return parameter the the saveLoginDataToLocal
                 * to save the user info to the local database...*/
                saveLoginDataToLocal(jsonResponse);
            }

            @Override
            public void onErrorResponse(String message) {
                /**
                 * This area indicates and catches the errors
                 * after unsuccessful request from the server.
                 * Message contains the error message comming
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
    private void saveLoginDataToLocal(JSONObject foValue){
        try {
            String sUserIDxx = foValue.getString("sUserIDxx");
            String sEmailAdd = foValue.getString("sEmailAdd");
            String sUserName = foValue.getString("sUserName");
            String dCreatedx = foValue.getString("dCreatedx");
            String dLoginxxx = (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())).format(new Date());
            Cursor loCursor = appData.getReadableDb().rawQuery("SELECT * FROM Client_Info_Master", (String[])null);
            int lnRow = loCursor.getCount();
            String lsSQL;
            loCursor.close();
            appData.getWritableDb().beginTransaction();
            if (lnRow <= 0) {
                lsSQL = "INSERT INTO Client_Info_Master (   sUserIDxx, sEmailAdd, sUserName, dLoginxxx, sMobileNo, dDateMmbr)  VALUES (" + SQLUtil.toSQL(sUserIDxx) + ", " + SQLUtil.toSQL(sEmailAdd) + ", " + SQLUtil.toSQL(sUserName) + ", " + SQLUtil.toSQL(dLoginxxx) + ", "+SQLUtil.toSQL(getMobileNo())+", "+SQLUtil.toSQL(dCreatedx)+")";
                appData.getWritableDb().execSQL(lsSQL);
                Log.d(SQLiteHandler.TAG, " Action Created: Insert");
            } else {
                lsSQL = "UPDATE Client_Info_Master SET sUserIDxx = " + SQLUtil.toSQL(sUserIDxx) + ", sEmailAdd = " + SQLUtil.toSQL(sEmailAdd) + ", sUserName = " + SQLUtil.toSQL(sUserName) + ", dLoginxxx = " + SQLUtil.toSQL(dLoginxxx);
                appData.getWritableDb().execSQL(lsSQL);
                Log.d(SQLiteHandler.TAG, " Action Created: Update");
            }
            appData.getWritableDb().setTransactionSuccessful();
            mResult = "success";
        } catch (JSONException var10) {
            var10.printStackTrace();
            Log.d(SQLiteHandler.TAG, "Catch Error: " + var10.getMessage());
            mResult = "error";
            mMessage = "Unknown error has been detected. Please try again.";
        } finally {
            appData.getWritableDb().endTransaction();
        }
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
