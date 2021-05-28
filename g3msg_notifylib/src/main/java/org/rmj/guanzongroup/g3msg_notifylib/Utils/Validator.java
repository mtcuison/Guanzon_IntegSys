package org.rmj.guanzongroup.g3msg_notifylib.Utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.dev.AppData;

public class Validator extends DataParser{
    private static final String TAG = Validator.class.getSimpleName();

    private Context mContext;
    private AppData appData;
    private SharedPref sharedPref;
    private UserInfoData userData;

    public Validator(Context context, RemoteMessage Data) {
        super(Data);
        this.mContext = context;
        this.appData = AppData.getInstance(mContext);
        this.userData = new UserInfoData(mContext);
        this.sharedPref = new SharedPref(mContext);
    }

    /**
     * check if the current user is valid to
     * receive the notification on the device.
     * */
    public boolean VALID_NOTIFICATION(){
        if(UserIDxx().equalsIgnoreCase(getValueOf("rcptid"))){
            return true;
        } else {
            return false;
        }
    }

    /**
     * User tables on the Sqlite Database are 2
     * User_Info_Master is only for the user's which are
     * Employees.
     *
     * Client_Info_Master is only for the user's which are client
     * this table is only use by GuanzonApp.*/
    private String UserIDxx(){
        if(sharedPref.ProducID().equalsIgnoreCase("GuanzonApp")){
            return userData.getClientInfoID();
        } else  if(sharedPref.ProducID().equalsIgnoreCase("Telecom")){
            return userData.getUserInfoID();
        } else {
            return userData.getUserInfoID();
        }
    }

    /**
     * This Method returns the result if
     * the current notification that's going to be viewed is
     * already READ/NOT...*/
    public boolean IS_READ(String MessageID){

        /**
         * This gets the notification details of
         * on local database...*/
        Cursor cursor = appData.getReadableDb().rawQuery("SELECT a.sMesgIDxx FROM Notification_Info_Master a " +
                "LEFT JOIN Notification_Info_Recepient b " +
                "ON a.sMesgIDxx = b.sTransNox " +
                "WHERE b.cMesgStat = '3' " +
                "AND a.sMsgTypex <> '00000' " +
                "AND a.sMesgIDxx = '"+MessageID+"'", null);
        if(cursor.getCount()>0){
            cursor.close();
            appData.close();
            Log.e(TAG, "Message Status : READ");
        return true;
        } else {
            cursor.close();
            appData.close();
            Log.e(TAG, "Message Status : UNREAD");
            return false;
        }
    }
}
