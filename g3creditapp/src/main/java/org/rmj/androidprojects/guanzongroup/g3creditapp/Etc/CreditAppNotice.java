package org.rmj.androidprojects.guanzongroup.g3creditapp.Etc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

import java.util.Random;

public class CreditAppNotice {
    private static final String TAG = CreditAppNotice.class.getSimpleName();

    private Context mContext;
    private String lsMessage;

    private static int CHANNEL_ID = 0;
    private static final String CHANNEL_DESC = "Application status update";
    private static final String CHANNEL_NAME = "Credit Application";
    private static final String NotificationID = "rmj.androidCreditApp";

    public CreditAppNotice(Context context, String Message, int channel_id){
        this.mContext = context;
        this.lsMessage = Message;
        CHANNEL_ID = channel_id;
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NotificationID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private NotificationCompat.Builder createNotification(){
        return new NotificationCompat.Builder(mContext)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setChannelId(NotificationID)
                .setContentTitle("Credit Application")
                .setSmallIcon(R.drawable.guanzo_small_logo)
                .setContentText(lsMessage);
    }

    public void showNotification(){
        createNotificationChannel();
        createNotification();
        NotificationManager ntfManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        ntfManager.notify(CHANNEL_ID, createNotification().build());
        Log.e(TAG, "Notification displayed");
    }

    public interface CHANNEL_ID{
        int ConnectionStatus = 2020021;
        int LocalDataUpdate = 2020022;
        int LocalServiceStatus = 2020023;
        int LocalReceiverStatus = 2020024;
        int IndividualMessage = new Random().nextInt();
    }
}
