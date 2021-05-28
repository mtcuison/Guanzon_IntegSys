package org.rmj.guanzongroup.g3msg_notifylib.DbHelper;

import android.content.Context;

import org.rmj.g3appdriver.dev.AppData;

public class NotificationData {

    private AppData db;

    public NotificationData(Context context){
        this.db = AppData.getInstance(context);
    }

    public void setupNotificationData() {
        try {
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS Notification_Info_Master (" +
                    "sTransNox varchar," +
                    "sMesgIDxx varchar," +
                    "sParentxx varchar," +
                    "dCreatedx date," +
                    "sAppSrcex varchar," +
                    "sCreatrID varchar," +
                    "sCreatrNm varchar," +
                    "sMsgTitle varchar," +
                    "sMessagex varchar," +
                    "sURLxxxxx varchar," +
                    "sDataSndx varchar," +
                    "sMsgTypex varchar," +
                    "cSentxxxx varchar," +
                    "dSentxxxx date)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS Notification_Info_Recepient(" +
                    "sTransNox varchar," +
                    "sAppRcptx varchar," +
                    "sRecpntID varchar," +
                    "sRecpntNm varchar," +
                    "sGroupIDx varchar," +
                    "sGroupNmx varchar," +
                    "cMonitorx varchar," +
                    "cMesgStat char," +
                    "cStatSent char," +
                    "dSentxxxx date," +
                    "dReceived date," +
                    "dReadxxxx date," +
                    "dLastUpdt date," +
                    "dTimeStmp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS Notification_User(" +
                    "sUserIDxx varchar," +
                    "sUserName varhcar)");
            db.getWritableDb().setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
    }
}
