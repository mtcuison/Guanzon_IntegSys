package org.rmj.guanzongroup.integsys.Services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.dev.AppData;
import org.rmj.guanzongroup.g3msg_notifylib.Builder.NotificationBuilder;
import org.rmj.guanzongroup.g3msg_notifylib.Utils.DataParser;
import org.rmj.guanzongroup.g3msg_notifylib.Utils.Validator;
import org.rmj.guanzongroup.integsys.Activity.MainActivity;

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = MessagingService.class.getSimpleName();

    private NotificationBuilder builder;
    private DataParser dataParser;

    private AppData appData;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        appData = AppData.getInstance(MessagingService.this);
        Log.e(TAG, "New Integsys Token RECIEVED : " + s);
        appData.setAppToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        builder = new NotificationBuilder(MessagingService.this, remoteMessage);
        dataParser = new DataParser(remoteMessage);
        Log.d(TAG, "A message from: " + remoteMessage.getFrom() + " " + remoteMessage.getData());
        Log.d(TAG, "From: " + remoteMessage.getFrom() + " " + remoteMessage.getData());
        if(new Validator(MessagingService.this, remoteMessage).VALID_NOTIFICATION()){
            builder.setIntent(new Intent(MessagingService.this, MainActivity.class));
            builder.CreateNotification();
        }
    }
}
