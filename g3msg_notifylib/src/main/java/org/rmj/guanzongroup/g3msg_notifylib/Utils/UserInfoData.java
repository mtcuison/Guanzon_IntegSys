package org.rmj.guanzongroup.g3msg_notifylib.Utils;

import android.content.Context;
import android.database.Cursor;

import org.rmj.g3appdriver.dev.AppData;


class UserInfoData {

    private Context context;
    private AppData appData;

    UserInfoData(Context sctx) {
        this.context = sctx;
        this.appData = AppData.getInstance(context);
    }

    String getUserInfoID(){
        Cursor cursor = appData.getReadableDb().rawQuery("SELECT sUserIDxx FROM User_info_Master", null);
        String id = "";
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            id = cursor.getString(0);
            cursor.close();
            return id;
        } else {
            cursor.close();
            return "";
        }
    }

    String getClientInfoID(){
        Cursor cursor = appData.getReadableDb().rawQuery("SELECT sUserIDxx FROM Client_info_Master", null);
        String id = "";
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            id = cursor.getString(0);
            cursor.close();
            return id;
        } else {
            cursor.close();
            return "";
        }
    }
}
