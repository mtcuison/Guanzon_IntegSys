package org.rmj.g3appdriver.utils.Http;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.StrictMode;

import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RequestHeaders {

    private Telephony telephony;
    private SharedPref sharedPref;
    private AppData appData;
    private SessionManager session;

    public RequestHeaders(Context AppContext) {
        this.appData = AppData.getInstance(AppContext);
        this.telephony = new Telephony(AppContext);
        this.sharedPref = new SharedPref(AppContext);
        this.session = new SessionManager(AppContext);
    }

    private Map<String, String> setHeaders(String ProductID) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Calendar calendar = Calendar.getInstance();

        String devImei = telephony.getDeviceID();
        String appToken = appData.getAppToken();
        String curr_dateTime = SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss");
        String devModel = Build.MODEL;

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", ProductID);
        headers.put("g-api-client", ClientID());
        headers.put("g-api-imei", devImei);
        headers.put("g-api-model", devModel);
        headers.put("g-api-mobile", MobileNO());
        headers.put("g-api-token", appToken);
        headers.put("g-api-user", UserID());
        headers.put("g-api-key", curr_dateTime);
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-log", LogNo());
        return headers;
    }

    public Map<String, String> getHeaders(){
        return setHeaders(sharedPref.ProducID());
    }

    private String MobileNO(){
        if(telephony.getMobileNoDb()==null || telephony.getMobileNoDb().equalsIgnoreCase("")){
            return sharedPref.getMobileNo();
        } else {
            return telephony.getMobileNoDb();
        }
    }

    private String ClientID(){
        String ClientID = "";
        if(session.isLoggedIn()) {
            switch (sharedPref.ProducID()) {
                case "GuanzonApp":
                    ClientID = "";
                case "Telecom":
                case "IntegSys":
                    ClientID = appData.getClientID();
            }
        } else {
            ClientID = "";
        }
        return ClientID;
    }

    private String LogNo(){
        switch (sharedPref.ProducID()){
            case "GuanzonApp":
                return "";
            case "Telecom":
            case "IntegSys":
                return appData.getLogNumber();
        }
        return "";
    }

    private String UserID(){
        String userID;
        switch (sharedPref.ProducID()){
            case "GuanzonApp":
                Cursor cursor = appData.getReadableDb().rawQuery("SELECT * FROM Client_Info_Master", null);
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    userID = cursor.getString(cursor.getColumnIndex("sUserIDxx"));
                    cursor.close();
                    return userID;
                }
                return "";
            case "Telecom":
                cursor = appData.getReadableDb().rawQuery("SELECT * FROM User_Info_Master", null);
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    userID = cursor.getString(cursor.getColumnIndex("sUserIDxx"));
                    cursor.close();
                    return userID;
                }

            case "IntegSys":
                cursor = appData.getReadableDb().rawQuery("SELECT * FROM User_Info_Master", null);
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    userID = cursor.getString(cursor.getColumnIndex("sUserIDxx"));
                    cursor.close();
                    return userID;
                }
                return "";
        }
        return "";
    }
}
