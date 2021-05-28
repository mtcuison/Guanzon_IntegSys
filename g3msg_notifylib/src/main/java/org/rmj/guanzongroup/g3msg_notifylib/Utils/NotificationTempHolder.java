package org.rmj.guanzongroup.g3msg_notifylib.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class NotificationTempHolder {
    private static final String TAG = NotificationTempHolder.class.getSimpleName();

    private Context mContext;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "DataMessage";
    private static final int PRIV_MODE = 0;
    private static final String temp_msgType = "MessageType";
    private static final String temp_msgIDxx = "MessageIDxx";
    private static final String temp_msgSndr = "MessageSndr";
    private static final String temp_msgDate = "MessageDate";
    private static final String temp_msgStat = "MessageStat";

    public NotificationTempHolder(Context context){
        this.mContext = context;
        this.pref = mContext.getSharedPreferences(PREF_NAME, PRIV_MODE);
        this.editor = pref.edit();
    }

    public void setMsgType(String msgType){
        editor.putString(temp_msgType, msgType);
        editor.commit();
        Log.e(TAG, "Message type has been set...");
    }

    public void setMsgIDxx(String msgIDxx){
        editor.putString(temp_msgIDxx, msgIDxx);
        editor.commit();
        Log.e(TAG, "Message ID has been set...");
    }

    public void setMsgSndr(String msgSndr){
        editor.putString(temp_msgSndr, msgSndr);
        editor.commit();
        Log.e(TAG, "Message sender has been set...");
    }

    public void setMsgDate(String msgDate){
        editor.putString(temp_msgDate, msgDate);
        editor.commit();
        Log.e(TAG, "Message date stamp has been set...");
    }

    public void setMsgStat(String msgStat){
        editor.putString(temp_msgStat, msgStat);
        editor.commit();
        Log.e(TAG, "Message status has been set...");
    }

    public String getMsgType(){
        return pref.getString(temp_msgType, "");
    }

    public String getMsgIDxx() {
        return pref.getString(temp_msgIDxx, "");
    }

    public String getMsgSndr() {
        return pref.getString(temp_msgSndr, "");
    }

    public String getMsgDate() {
        return pref.getString(temp_msgDate, "");
    }

    public String getMsgStat() {
        return pref.getString(temp_msgStat, "");
    }
}
