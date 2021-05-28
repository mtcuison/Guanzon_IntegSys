package org.rmj.guanzongroup.g3msg_notifylib.Builder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.rmj.guanzongroup.g3msg_notifylib.Utils.DataParser;
import org.rmj.guanzongroup.g3msg_notifylib.DbHelper.NotificationDataHelper;
import org.rmj.guanzongroup.g3msg_notifylib.R;
import org.rmj.guanzongroup.g3msg_notifylib.Utils.SharedPref;

import java.util.Random;

public class NotificationReceiver {
    private Context mContext;
    private SharedPref sharedPref;
    private onNotificationReceiveListener mListener;
    private NotificationDataHelper dataHelper;

    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;

    private static final int CHANNEL_ID = new Random().nextInt();
    private static final String NOTIFICATION_CHANNEL = "g3Notifications";
    private static final String CHANNEL_DESCRIPTION = "GUANZON APP DIGITAL GCARD.";
    private static final String CHANNEL_NAME = "GuanzonAppUtils";
    private static final String NOTIFICATION_ID = "rmj.g3AndroidAppUtils";

    public NotificationReceiver(Context context){
        this.mContext = context;
        this.dataHelper = new NotificationDataHelper(mContext);
        this.sharedPref = new SharedPref(mContext);
    }

    /**
     * The initialization of java classess and getting the
     * Context of the class extension.
     * DataParser parses the RemoteMessage and the DataMessage inside it.
     * DataHelper manipulates the SQLite Database to
     * Insert the data From the RemoteMessage.*/
    public void saveNotification(final RemoteMessage DataMessage, onNotificationReceiveListener lister) {
        this.mListener = lister;
        DataParser dataParser = new DataParser(DataMessage);
        dataHelper.setData(DataMessage);
        dataHelper.saveNotificationData();
        if (mListener != null) {
            mListener.onRecievedNotificationListener(dataHelper.getMessageID());
            createNotificationChannel();
            showNotification();
        }
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * smallIcon and Large icon has a value that came from
     * NotificationAssets which is based on the value of
     * JSONObject String key from NotificationBuilder...
     * */
    private NotificationCompat.Builder createNotification(){
        builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_g_logo)
                .setContentTitle(getJsonData("title"))
                .setContentText(getJsonData("message"))
                .setLargeIcon(NotificationAssets.getNotificationLargeIcon(
                        mContext, getJsonData("msgtype")))
                .setOngoing(NotificationAssets.getNotificationStatus(
                        getJsonData("msgtype")))
                .setAutoCancel(true)
                .setChannelId(NOTIFICATION_ID)
                .setPriority(NotificationAssets.getNotificationPriority(
                        sharedPref.ProducID(), getJsonData("msgtype")))
                .setContentIntent(mListener.onCreateNotification());
        return builder;
    }

    /**
     * This function return a string that
     * a JSONObject from NotificationBuilder.class has pass through
     * onNotificationReceiveListener...
     * Get string requires a specific key of JSONObject
     * */
    private String getJsonData(String key){
        try{
            /**
             * notificationData is a JSONObject from
             * NotificationBuilder...*/
            return mListener.notificationData().getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Method to show Notification on NotificationTray...*/
    private void showNotification(){
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(CHANNEL_ID, createNotification().build());
    }
}
