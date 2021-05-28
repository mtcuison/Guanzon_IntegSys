package org.rmj.g3appdriver.etc;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPref {

    private static String TAG = SharedPref.class.getSimpleName();

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private static final String Pref_name = "Temp_Data";
    private static final String DashBoadActivity = "DasboardMain";
    private static final String temp_ProductID = "ProductID";
    private static final String temp_DeviceID = "DeviceID";
    private static final String temp_DateLogin = "DateLogin";
    private static final String temp_sessionExp = "SessionExp";
    private static final String temp_mobileno = "MobileNo";
    private static final String temp_PIN = "ConfirmationPIN";

    public SharedPref(Context context){
        int priv_Mode = 0;
        pref = context.getSharedPreferences(Pref_name, priv_Mode);
        editor = pref.edit();
    }

    public void setDashBoadActivity(String MenuActivity){
        editor.putString(DashBoadActivity, MenuActivity);
        editor.commit();

        Log.d(TAG, "Main menu activity has been set");
    }
    public String MenuActivity(){
        return pref.getString(DashBoadActivity, "");
    }

    public void setTemp_ProductID(String ProductID){
        editor.putString(temp_ProductID, ProductID);
        if(!editor.commit()){
            editor.apply();
            Log.d(TAG, "ProductID has been set. Value: " + pref.getString(temp_ProductID, "null"));
        }
    }
    public String ProducID(){
        return  pref.getString(temp_ProductID,"");
    }

    public void setTemp_DeviceID(String DeviceID){
        editor.putString(temp_DeviceID, DeviceID);
        editor.commit();
    }
    public String DeviceID(){
        return pref.getString(temp_DeviceID, "");
    }

    public void setTemp_DateLogin(String DateLogin){
        editor.putString(temp_DateLogin, DateLogin);
        editor.commit();
    }
    public String DateLogin(){
        return  pref.getString(temp_DateLogin, "");
    }

    public void setTemp_sessionExp(String SessionExp){
        editor.putString(temp_sessionExp, SessionExp);
        editor.commit();
    }
    public String SessionExp(){
        return pref.getString(temp_sessionExp, "");
    }

    public void setTemp_mobileno(String MobileNo){
        editor.putString(temp_mobileno, MobileNo);
        editor.commit();
    }

    public String getMobileNo(){
        return pref.getString(temp_mobileno, "");
    }

    public void setTemp_PIN(String PIN){
        editor.putString(temp_PIN, PIN);
        editor.commit();
    }

    public String getPIN(){
        return pref.getString(temp_PIN, "");
    }
}
