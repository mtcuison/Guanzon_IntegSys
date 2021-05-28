package org.rmj.guanzongroup.g3msg_notifylib.DbHelper;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.guanzongroup.g3msg_notifylib.Utils.DataParser;

import java.util.Calendar;

public class NotificationDataHelper {
    private static final String TAG = NotificationDataHelper.class.getSimpleName();

    private AppData db;
    private DataParser parser;
    private Calendar calendar;

    private String MessageID = "";

    public NotificationDataHelper(Context context){
        /**THIS CLASS MANIPULATES THE SQLITE DATABASE ACCORDING THE USER ACTION
         * ON VIEWING THE NOTIFICATION*/
        this.db = AppData.getInstance(context);
        this.calendar = Calendar.getInstance();
    }

    public void setData(RemoteMessage data) {
        parser = new DataParser(data);
    }

    public void saveNotificationData(){
        saveIntoMasterTable();
        saveIntoRecepientTable();
        saveSourceUserTable();
        MessageID = parser.getValueOf("transno");
    }

    public String getMessageID() {
        return MessageID;
    }

    private void saveIntoMasterTable(){
        try {
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("INSERT INTO Notification_Info_Master(" +
                    "sTransNox, " +
                    "sMesgIDxx, " +
                    "sParentxx, " +
                    "dCreatedx, " +
                    "sAppSrcex, " +
                    "sCreatrID, " +
                    "sCreatrNm, " +
                    "sMsgTitle, " +
                    "sMessagex, " +
                    "sMsgTypex) VALUES (" +
                    "'" + getLocalTransNox() + "', " +
                    "'" + parser.getValueOf("transno") + "', " +
                    "'" + parser.getValueOf("parent") + "', " +
                    "'" + parser.getValueOf("stamp") + "', " +
                    "'" + parser.getValueOf("appsrce") + "', " +
                    "'" + parser.getValueOf("srceid") + "', " +
                    "'" + parser.getValueOf("srcenm") + "', " +
                    "'" + parser.getDataValueOf("title") + "', " +
                    "'" + parser.getDataValueOf("message") + "', " +
                    "'" + parser.getValueOf("msgmon") + "')");
            db.getWritableDb().setTransactionSuccessful();
            Log.e(TAG, "Notification data has been save successfully in master's table.");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    private void saveIntoRecepientTable(){
        try {
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("INSERT INTO Notification_Info_Recepient(" +
                    "sTransNox, " +
                    "sAppRcptx, " +
                    "sRecpntID, " +
                    "sRecpntNm, " +
                    "cMesgStat, " +
                    "dReceived) " +
                    "VALUES (" +
                    "'" + parser.getValueOf("transno") + "', " +
                    "'" + parser.getValueOf("apprcpt") + "', " +
                    "'" + parser.getValueOf("rcptid") + "', " +
                    "'" + parser.getValueOf("rcptnm") + "', " +
                    "'" + parser.getValueOf("status") + "', " +
                    "'" + getCurrentDateTime() + "')");
            db.getWritableDb().setTransactionSuccessful();
            Log.e(TAG, "Notification data has been save successfully in recipient's table.");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    private void saveSourceUserTable(){
        try {
            if (!isUserExists()) {
                db.getWritableDb().execSQL("INSERT INTO Notification_User(" +
                        "sUserIDxx, " +
                        "sUserName) " +
                        "VALUES (" +
                        "'" + parser.getValueOf("srceid") + "'," +
                        "'" + parser.getValueOf("srcenm") + "')");
                db.getWritableDb().setTransactionSuccessful();
                db.getWritableDb().endTransaction();
            }
            Log.e(TAG, "Notification user's data has been save successfully in users's table.");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isUserExists(){
        String userID = parser.getValueOf("srceid");
        Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM Notification_User WHERE sUserIDxx = '"+userID+"'", null);
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    private String getCurrentDateTime(){
        return SQLUtil.dateFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    private String getLocalTransNox(){
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int count = 12;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        Log.e(TAG, "Notification local transnox has been created. Value : " + builder.toString());
        return builder.toString();
    }

    public void updateNotificationStatus(String messageIDxx, int Status){
        try {
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("UPDATE Notification_Info_Recepient SET dReadxxxx = '" + getCurrentDateTime() + "', " +
                    "dLastUpdt = '" + getCurrentDateTime() + "', " +
                    "cMesgStat = '" + Status + "', " +
                    "cStatSent = '1' " +
                    "WHERE sTransNox = '" + messageIDxx + "'");
            db.getWritableDb().setTransactionSuccessful();
            Log.e(TAG, "Notification data of " + messageIDxx + " has been updated successfully. Online Status : " + Status);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    public void updateOfflineNotificationStatus(String messageIDxx, int Status){
        try {
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("UPDATE Notification_Info_Recepient SET dReadxxxx = '" + getCurrentDateTime() + "', " +
                    "dLastUpdt = '" + getCurrentDateTime() + "', " +
                    "cMesgStat = '" + Status + "', " +
                    "cStatSent = '0'" +
                    "WHERE sTransNox = '" + messageIDxx + "'");
            db.getWritableDb().setTransactionSuccessful();
            Log.e(TAG, "Notification data of " + messageIDxx + " has been updated successfully. Offline Status : " + Status);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    public boolean isRead(String MesgIDxx){
        Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM Notification_Info_Recepient WHERE cMesgStat = '3' AND sTransNox = '"+MesgIDxx+"'", null);
        if(cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }
}
