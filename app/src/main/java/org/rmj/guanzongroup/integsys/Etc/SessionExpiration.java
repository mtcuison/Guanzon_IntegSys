package org.rmj.guanzongroup.integsys.Etc;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.etc.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SessionExpiration {
    private static final String TAG = SessionExpiration.class.getSimpleName();

    private Context mContext;

    public SessionExpiration(Context context){
        this.mContext = context;
    }

    public boolean isSessionExpired(){
        try {
            if (isValidToday()) {
                AppData db = AppData.getInstance(mContext);
                try {
                    Cursor cursor = db.getReadableDb().rawQuery("SELECT strftime('%H:%M:%S', 'now', 'localtime') - strftime('%H:%M:%S', dSessionx) FROM User_Info_Master", null);
                    cursor.moveToFirst();
                    int liSession = Integer.parseInt(cursor.getString(0));
                    Log.e(TAG, "Date session started : " + liSession);
                    cursor.close();
                    if (liSession <= 0) {
                        Log.e(TAG, "Session valid.");
                        return true;
                    }
                    Log.e(TAG, "Session expired. Please re-login.");
                    new SessionManager(mContext).setLogin(false);
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "First Login attempt, Session checking not applicable");
                    return true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        new SessionManager(mContext).setLogin(false);
        return false;
    }

    private boolean isValidToday() throws ParseException {
        AppData db = AppData.getInstance(mContext);
        Cursor loCursor = db.getReadableDb().rawQuery("SELECT dSessionx FROM User_Info_Master", null);
        if(loCursor.getCount() > 0){
            loCursor.moveToFirst();
            Date loSessionDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(loCursor.getString(0));
            String lsDateLog = new SimpleDateFormat("yyyy-MM-dd").format(loSessionDate);
            SimpleDateFormat loFormater = new SimpleDateFormat("yyyy-MM-dd");
            Date loDate = new Date();
            String lsDateNow = loFormater.format(loDate);
            if(lsDateLog.equalsIgnoreCase(lsDateNow)){
                loCursor.close();
                return true;
            }
        } else {
            loCursor.close();
            return true;
        }
        loCursor.close();
        return false;
    }
}
