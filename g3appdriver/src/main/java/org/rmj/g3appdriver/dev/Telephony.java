package org.rmj.g3appdriver.dev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.Settings;

import org.rmj.g3appdriver.etc.SharedPref;

public class Telephony {

    private Context mContext;

    private AppData appData;

    public Telephony(Context context){
        this.mContext = context;
        this.appData = AppData.getInstance(mContext);
    }

    @SuppressLint("HardwareIds")
    public String getDeviceID() {
        return Settings.Secure.getString(
                mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public String getMobileNoDb(){
        String MobileNo = new SharedPref(mContext).getMobileNo();
        if(MobileNo.isEmpty()) {
            Cursor cursor = appData.getReadableDb().rawQuery("SELECT * FROM " + getTableName() + "", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                String lnMobileNo = cursor.getString(cursor.getColumnIndex("sMobileNo"));
                cursor.close();
                return lnMobileNo;
            }
        }
        return MobileNo;
    }

    private String getTableName(){
        String ProductID = new SharedPref(mContext).ProducID();
        switch (ProductID){
            case "GuanzonApp":
                return "Client_Info_Master";
            case "Telecom":
            case "IntegSys":
                return "User_Info_Master";
        }
        return "";
    }
}
