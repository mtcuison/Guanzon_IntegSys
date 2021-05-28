package org.rmj.guanzongroup.g3msg_notifylib.DbHelper;

import android.content.Context;
import android.database.Cursor;

import org.rmj.g3appdriver.dev.AppData;
import org.rmj.guanzongroup.g3msg_notifylib.Model.NotificationMessageModel;

import java.util.ArrayList;
import java.util.List;

public class Get_NotificationData {
    private static final String TAG = Get_NotificationData.class.getSimpleName();

    private AppData appData;
    private ArrayList<ArrayList<String>> mArr_NotificationsList;
    private ArrayList arr_msgid;
    private ArrayList arr_Title;
    private ArrayList arr_datex;
    private ArrayList arr_messg;
    private ArrayList arr_typex;
    private ArrayList arr_statx;
    private ArrayList arr_srcid;
    private ArrayList arr_appsc;
    private ArrayList arr_srcnm;

    public ArrayList getNotifications(Context context, String UserID){
        setupArrays();
        appData = AppData.getInstance(context);
        Cursor cursor = appData.getReadableDb().rawQuery("SELECT a.*, b.* FROM Notification_Info_Master a " +
                "LEFT JOIN Notification_Info_Recepient b " +
                "ON a.sMesgIDxx = b.sTransNox " +
                "WHERE b.cMesgStat <> '5' AND " +
                "a.sMsgTypex <> '00000' AND " +
                "b.sRecpntID = '"+UserID+"'", null);
        int row = cursor.getCount();
        if(row>0){
            cursor.moveToFirst();
            for(int x = 0; x < row; x++) {
                arr_msgid.add(cursor.getString(cursor.getColumnIndex("sMesgIDxx")));    //0
                arr_appsc.add(cursor.getString(cursor.getColumnIndex("sAppSrcex")));    //1
                arr_datex.add(cursor.getString(cursor.getColumnIndex("dReceived")));    //2
                arr_messg.add(cursor.getString(cursor.getColumnIndex("sMessagex")));    //3
                arr_srcid.add(cursor.getString(cursor.getColumnIndex("sCreatrID")));    //4
                arr_srcnm.add(cursor.getString(cursor.getColumnIndex("sCreatrNm")));    //5
                arr_statx.add(cursor.getString(cursor.getColumnIndex("cMesgStat")));    //6
                arr_Title.add(cursor.getString(cursor.getColumnIndex("sMsgTitle")));    //7
                arr_typex.add(cursor.getString(cursor.getColumnIndex("sMsgTypex")));    //8
                cursor.moveToNext();
            }
        }
        cursor.close();

        mArr_NotificationsList.add(arr_msgid);
        mArr_NotificationsList.add(arr_appsc);
        mArr_NotificationsList.add(arr_datex);
        mArr_NotificationsList.add(arr_messg);
        mArr_NotificationsList.add(arr_srcid);
        mArr_NotificationsList.add(arr_srcnm);
        mArr_NotificationsList.add(arr_statx);
        mArr_NotificationsList.add(arr_Title);
        mArr_NotificationsList.add(arr_typex);
        return mArr_NotificationsList;
    }

    private void setupArrays(){
        mArr_NotificationsList = new ArrayList<>();
        arr_msgid = new ArrayList();
        arr_appsc = new ArrayList();
        arr_datex = new ArrayList();
        arr_messg = new ArrayList();
        arr_srcid = new ArrayList();
        arr_srcnm = new ArrayList();
        arr_statx = new ArrayList();
        arr_Title = new ArrayList();
        arr_typex = new ArrayList();
    }

    /**
     * This functions executes a query that will fetch data from the database...
     * Context must be pass to open the Sqlite Database and
     * collect all the data from both Notification_Info_Master and Notification_Info_Recepient
     * getInfoName is the returnValue of whatever the command want's to get
     * to the Local Database.*/
    private String getMessageInfo(Context context, String MessageID, String getInfoName){
        appData = AppData.getInstance(context);
        String returnValue = "";
        Cursor cursor = appData.getReadableDb().rawQuery("SELECT a.*, b.* FROM Notification_Info_Master a " +
                        "LEFT JOIN Notification_Info_Recepient b " +
                        "ON a.sMesgIDxx = b.sTransNox " +
                        "WHERE b.cMesgStat <> '5' " +
                        "AND a.sMsgTypex <> '0' " +
                        "AND b.sRecpntID = '"+appData.getUserID()+"'" +
                        "AND a.sMesgIDxx = '"+MessageID+"'", null);
        int row = cursor.getCount();
        if(row>0){
            cursor.moveToFirst();
            returnValue = cursor.getString(cursor.getColumnIndex(getInfoName));
            cursor.close();
        }
        return returnValue;
    }

    /**
     * Get the SourceName of the notification/message to be shown
     * int The Activity_NotificationViewer*/
    public String getMsgSourceName(Context context, String MessageID){
        return getMessageInfo(context, MessageID, "sCreatrNm");
    }

    public List<NotificationMessageModel> getEmployeeMessages(Context context, String UserID){
        List<NotificationMessageModel> loNotifications = new ArrayList<>();
        try {
            appData = AppData.getInstance(context);
            Cursor cursor = appData.getReadableDb().rawQuery("SELECT a.*, b.* FROM Notification_Info_Master a " +
                    "LEFT JOIN Notification_Info_Recepient b " +
                    "ON a.sMesgIDxx = b.sTransNox " +
                    "WHERE b.cMesgStat <> '5' " +
                    "AND a.sMsgTypex <> '00000' " +
                    "AND b.sRecpntID = '" + UserID + "'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++) {
                    loNotifications.add(new NotificationMessageModel(
                            cursor.getString(cursor.getColumnIndex("sMesgIDxx")),
                            cursor.getString(cursor.getColumnIndex("sMsgTitle")),
                            cursor.getString(cursor.getColumnIndex("sCreatrNm")),
                            cursor.getString(cursor.getColumnIndex("sMessagex")),
                            cursor.getString(cursor.getColumnIndex("dReceived")),
                            cursor.getString(cursor.getColumnIndex("dReceived"))));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return loNotifications;
    }
}
