package org.rmj.guanzongroup.g3msg_notifylib.DbHelper;

import android.content.Context;
import android.database.Cursor;

import org.rmj.g3appdriver.dev.AppData;
import org.rmj.guanzongroup.g3msg_notifylib.R;

public class STATUS {
    public static int NOTIFICATION_STATUS(Context context, String MessageID){
        AppData appData = AppData.getInstance(context);
        Cursor cursor = appData.getReadableDb().rawQuery("SELECT a.sMesgIDxx FROM Notification_Info_Master a " +
                "LEFT JOIN Notification_Info_Recepient b " +
                "ON a.sMesgIDxx = b.sTransNox " +
                "WHERE b.cMesgStat = '3' " +
                "AND a.sMsgTypex <> '00000' " +
                "AND a.sMesgIDxx = '"+MessageID+"'", null);
        int row = cursor.getCount();
        if(row>0){
            cursor.close();
            return R.color.readNotification;
        } else {
            cursor.close();
            return R.color.unReadNotification;
        }
    }
}
